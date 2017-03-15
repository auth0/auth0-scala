package controllers

import javax.inject.Inject

import helpers.Auth0Config
import play.api.mvc.{Action, Controller}

class Application @Inject()(config: Auth0Config) extends Controller {

  def index = Action {
    Ok(views.html.index(config))
  }
}
