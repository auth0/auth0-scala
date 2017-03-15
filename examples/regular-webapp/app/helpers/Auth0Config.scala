package helpers

import javax.inject.{Inject, Singleton}

import play.api.Configuration

@Singleton
class Auth0Config @Inject()(configuration: Configuration) {

  val secret: String = configuration.getString("auth0.clientSecret").get
  val clientId: String = configuration.getString("auth0.clientId").get
  val callbackURL: String = configuration.getString("auth0.callbackURL").get
  val domain: String = configuration.getString("auth0.domain").get

}
