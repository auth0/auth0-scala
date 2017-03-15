package controllers

import javax.inject._

import helpers.Auth0Config
import play.api.cache._
import play.api.http.{HeaderNames, MimeTypes}
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import play.api.libs.json.{JsValue, Json, OFormat}
import play.api.libs.ws.WSClient
import play.api.mvc.{Action, AnyContent, Controller, Result}

import scala.concurrent.{ExecutionContext, Future}

case class Tokens(idToken: String, accessToken: String)

object Tokens {
  implicit val format: OFormat[Tokens] = Json.format[Tokens]
}

@Singleton
class Callback @Inject()(
  cache: CacheApi,
  ws: WSClient,
  config: Auth0Config
)(implicit ec: ExecutionContext) extends Controller {

  val auth0host: String = s"https://${config.domain}"
  val badRequest: Future[Result] = Future.successful(BadRequest(Json.obj("error" -> "No parameters supplied")))

  def callback(codeOpt: Option[String] = None): Action[AnyContent] = Action.async {
    def login(code: String): Future[Result] = {
      val response = for {
        Tokens(idToken, accessToken) <- getToken(code)
        user: JsValue <- getUser(accessToken)
      } yield {
        cache.set(User.cacheKey(idToken), user)
        Redirect(routes.User.index()).withSession(
          "idToken" -> idToken,
          "accessToken" -> accessToken
        )
      }
      response.recover { case _ => Unauthorized("Tokens not sent") }
    }

    codeOpt.fold(badRequest)(login)
  }

  private def getToken(code: String): Future[Tokens] =
    ws
      .url(s"$auth0host/token")
      .withHeaders(HeaderNames.ACCEPT -> MimeTypes.JSON)
      .post(
        Json.obj(
          "client_id" -> config.clientId,
          "client_secret" -> config.secret,
          "redirect_uri" -> config.callbackURL,
          "code" -> code,
          "grant_type" -> "authorization_code"
        )
      )
      .map(_.json.as[Tokens])

  private def getUser(accessToken: String): Future[JsValue] =
    ws
      .url(s"$auth0host/userinfo")
      .withQueryString("access_token" -> accessToken)
      .get()
      .map(_.json)

}
