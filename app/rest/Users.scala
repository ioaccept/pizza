package rest

import models.User
import play.api.libs.json.{JsValue, JsError, Json, Writes}
import play.api.mvc.{Action, AnyContent, BodyParsers, Controller, RequestHeader}
import services.UserService

/**
 * REST API for the User Class.
 */
object Users extends Controller {

  private case class HateoasUser(user: User, url: String)

  private def mkHateoasUser(user: User)(implicit request: RequestHeader): HateoasUser = {
    val url = routes.Users.user(user.id).absoluteURL()
    HateoasUser(user, url)
  }

  private implicit val hateoasUserWrites = new Writes[HateoasUser] {
    def writes(huser: HateoasUser): JsValue = Json.obj(
      "user" -> Json.obj(
        "id" -> huser.user.id,
        "name" -> huser.user.name
      ),
      "links" -> Json.arr(
        Json.obj(
          "rel" -> "self",
          "href" -> huser.url,
          "method" -> "GET"
        ),
        Json.obj(
          "rel" -> "remove",
          "href" -> huser.url,
          "method" -> "DELETE"
        )
      )
    )
  }

  /**
   * Get all users.
   * {{{
   * curl --include http://localhost:9000/api/users
   * }}}
   * @return all users in a JSON representation.
   */
  def users: Action[AnyContent] = Action { implicit request =>
    val users = UserService.registeredUsers
    Ok(Json.obj(
      "users" -> Json.toJson(users.map { user => Json.toJson(mkHateoasUser(user)) }),
      "links" -> Json.arr(
        Json.obj(
          "rel" -> "self",
          "href" -> routes.Users.users.absoluteURL(),
          "method" -> "GET"
        ),
        Json.obj(
          "rel" -> "create",
          "href" -> routes.Users.addUser.absoluteURL(),
          "method" -> "POST"
        )
      )
    )
    )
  }

  /**
   * Gets a user by id.
   * Use for example
   * {{{
   * curl --include http://localhost:9000/api/user/1
   * }}}
   * @param id user id.
   * @return user info in a JSON representation.
   */
  def user(id: Long): Action[AnyContent] = Action { implicit request =>
    UserService.registeredUsers.find {
      _.id == id
    }.headOption.map { user =>
      Ok(Json.toJson(mkHateoasUser(user)))
    }.getOrElse(NotFound)
  }

  private case class Username(name: String)
  private implicit val usernameReads = Json.reads[Username]

  /**
   * Create a new user by a POST request including the user name as JSON content.
   * Use for example
   * {{{
   * curl --include --request POST --header "Content-type: application/json"
   *      --data '{"name" : "WieAuchImmer"}' http://localhost:9000/api/user
   * }}}
   * @return info about the new user in a JSON representation
   */
 def addUser: Action[JsValue] = Action(BodyParsers.parse.json) { implicit request =>
    val username = request.body.validate[Username]
    username.fold(
      errors => {
        BadRequest(Json.obj("status" -> "OK", "message" -> JsError.toFlatJson(errors)))
      },
      username => {
        Ok(Json.obj("status" -> "OK",
          "user" -> Json.toJson(mkHateoasUser(UserService.addUser(username.name, username.name, 5, username.name))))) // Noch ändern wenn gebraucht
      }
    )
  }

  /**
   * Delete a user by id using a DELETE request.
   * {{{
   * curl --include --request DELETE http://localhost:9000/api/user/1
   * }}}
   * @param id the user id.
   * @return success info or NotFound
   */
  def rmUser(id: Long): Action[AnyContent] = Action { implicit request =>
    val success = UserService.rmUser(id)
    if (success)
      Ok(Json.obj("status" -> "OK"))
    else
      NotFound
  }
}
