import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
@RunWith(classOf[JUnitRunner])
class ApplicationSpec extends Specification {

  "Application" should {

    "send 404 on a bad request" in new WithApplication{
      route(FakeRequest(GET, "/boum")) must beNone
    }

    "render the index page" in new WithApplication{
      val home = route(FakeRequest(GET, "/")).get

      status(home) must equalTo(OK)
      contentType(home) must beSome.which(_ == "text/html")
      contentAsString(home) must contain ("Willkommen")
    }

    "add a user" in new WithApplication{
      val username = "TestUser"
      val result = route(FakeRequest(POST, "/addUser").withFormUrlEncodedBody(
          "Name" -> username
        )).get

      status(result) must_== SEE_OTHER

      val nextUrl = redirectLocation(result) match {
        case Some(s: String) => s
        case _ => ""
      }
      nextUrl must contain("/welcomeUser?username=" + username)

      val newResult = route(FakeRequest(GET, nextUrl)).get

      status(newResult) must equalTo(OK)
      contentType(newResult) must beSome.which(_ == "text/html")
      contentAsString(newResult) must contain("Willkommen " + username)

    }
  }
}
