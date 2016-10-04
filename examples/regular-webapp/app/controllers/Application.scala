package controllers

import play.api.mvc.Action
import play.api.mvc.Controller
import helpers.Auth0Config
import play.api.Logger
import javax.inject.Inject

class Application @Inject() (config:Auth0Config) extends Controller {

  def index = Action {
    Ok(views.html.index(config))
  }
}