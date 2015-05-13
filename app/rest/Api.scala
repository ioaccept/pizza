package rest

import play.api.mvc.{Action, AnyContent, Controller}
import play.api.libs.json.Json

/**
 * The root element of the REST API.
 */
object Api extends Controller {
  /**
   * The entry point of the REST API.
   * Use for example
   * {{{
   * curl --include http://localhost:9000/api
   * }}}
   * @return just links.
   */
  def api: Action[AnyContent] = Action { implicit request =>
    Ok(Json.obj(
      "links" -> Json.arr(
        Json.obj(
          "rel" -> "self",
          "href" -> routes.Api.api.absoluteURL(),
          "method" -> "GET"
        ),
        Json.obj(
          "rel" -> "users",
          "href" -> routes.Users.users.absoluteURL(),
          "method" -> "GET"
        )
      )
    )
    )
  }
}
