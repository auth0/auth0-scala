package helpers

import play.api.Play
import play.api.Configuration
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Auth0Config @Inject() (configuration: Configuration) {
  
  val secret = configuration.getString("auth0.clientSecret").get
  val clientId = configuration.getString("auth0.clientId").get
  val callbackURL = configuration.getString("auth0.callbackURL").get
  val domain = configuration.getString("auth0.domain").get
  
}
