import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._
import services.UserService

@RunWith(classOf[JUnitRunner])
class UserServiceSpec extends Specification {

  def memDB[T](code: => T) =
    running(FakeApplication(additionalConfiguration = Map(
      "db.default.driver" -> "org.h2.Driver",
      "db.default.url" -> "jdbc:h2:mem:test;MODE=PostgreSQL"
    )))(code)

  "The UserService" should {

    "return a empty list of users first" in memDB {
      UserService.registeredUsers must beEmpty
    }

    "add a user Helge" in memDB {
      UserService.addUser("Helge").name must be equalTo("Helge")
    }

    "add a user Helga" in memDB {
      UserService.addUser("Helga").name must be equalTo("Helga")
    }

    "return a list containing just Helge after adding user Helge" in memDB {
      UserService.addUser("Helge")
      val registeredUsers = UserService.registeredUsers
      registeredUsers.length must be equalTo(1)
      registeredUsers(0).name must be equalTo("Helge")
    }

    "return a list of two users after adding two users Helge" in memDB {
      UserService.addUser("Helge")
      UserService.addUser("Helge")
      UserService.registeredUsers.length must be equalTo(2)
    }

  }

}
