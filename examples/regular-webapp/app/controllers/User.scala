package controllers

import javax.inject._

import play.api.cache._
import play.api.libs.json._
import play.api.mvc.{Action, Controller, _}

object User {
  def cacheKey(idToken: String): String = s"$idToken profile"
}

class User @Inject()(cache: CacheApi) extends Controller {

  val goToHomePage: Result = Redirect(routes.Application.index())

  def AuthenticatedAction(f: (Request[AnyContent], JsValue) => Result): Action[AnyContent] = Action { request =>
    val maybeUser: Option[JsValue] = request
      .session
      .get("idToken")
      .flatMap(idToken => cache.get(User.cacheKey(idToken)))

    maybeUser.fold(goToHomePage)(user => f(request, user))
  }

  def index = AuthenticatedAction { (_, user) => Ok(views.html.user(user)) }

}
