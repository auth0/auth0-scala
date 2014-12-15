package helpers

import play.api.Play

case class Auth0Config(secret: String, clientId: String, callbackURL: String, domain: String)
object Auth0Config {
  def get() = {
    Auth0Config(
          Play.current.configuration.getString("auth0.clientSecret").get,
          Play.current.configuration.getString("auth0.clientId").get,
          Play.current.configuration.getString("auth0.callbackURL").get,
          Play.current.configuration.getString("auth0.domain").get
    )
  }
}