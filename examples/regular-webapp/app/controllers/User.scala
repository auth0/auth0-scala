package controllers

import play.api._
import play.api.mvc._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.http.{MimeTypes, HeaderNames}
import play.api.libs.ws.WS
import play.api.mvc.{Results, Action, Controller}
import play.api.libs.json._
import play.api.cache.Cache
import play.api.Play.current
import play.mvc.Results.Redirect


object User extends Controller {
  def AuthenticatedAction(f: Request[AnyContent] => Result): Action[AnyContent] = {
    Action { request =>
      (request.session.get("idToken").flatMap { idToken =>
        Cache.getAs[JsValue](idToken + "profile")
      } map { profile =>
        f(request)
      }).orElse {
        Some(Redirect(routes.Application.index()))
      }.get
    }
  }
  
  def index = AuthenticatedAction { request =>
    val idToken = request.session.get("idToken").get
    val profile = Cache.getAs[JsValue](idToken + "profile").get
    Ok(views.html.user(profile))
  }
}