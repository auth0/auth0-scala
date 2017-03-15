import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import play.api.test.Helpers._
import play.api.test._

/**
  * Add your spec here.
  * You can mock out a whole application including requests, plugins etc.
  * For more information, consult the wiki.
  */
class ApplicationSpec extends PlaySpec with GuiceOneServerPerSuite {

  "Application" should {

    "send 404 on a bad request" in  {
      val result = route(app, FakeRequest(GET, "/boum"))
      result.map(status) mustBe Some(404)
    }

    "render the index page" in {
      val result = route(app, FakeRequest(GET, "/"))

      result.map(status) mustBe Some(OK)
      result.flatMap(contentType) mustBe Some("text/html")
    }
  }
}
