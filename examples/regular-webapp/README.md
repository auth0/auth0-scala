# Auth0 + Play 2 Scala Regular WebApp Seed
This is the seed project you need to use if you're going to create regular Webapp with Scala and Play2. If you want to create a Play Scala API to use with your SPA or mobile app, please check [this other seed project](TODO://)

#Running the example
In order to run the example you need to have `play` and `activator` installed.

You also need to set the ClientSecret, ClientId, CallbackURL and Domain from Auth0 in the `application.conf`. Just look for the placeholders and fill them with the right information


````properties
# Auth0 Information
# ~~~~~~~~~~~~~~~~~

auth0.clientSecret="{CLIENT_SECRET}"
auth0.clientId="{CLIENT_ID}"
auth0.domain="{DOMAIN}"
auth0.callbackURL="http://localhost:9000/callback"
````

Finally, run `activator run` to start the app and try calling [http://localhost:9000/](http://localhost:9000/)
