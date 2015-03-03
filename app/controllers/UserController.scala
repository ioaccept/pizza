package controllers

import play.api.mvc.{Action, AnyContent, Controller}
import play.api.data.Form
import play.api.data.Forms.{mapping,text}
import services.UserService
import forms.CreateUserForm

/**
 * Controller for user specific operations.
 *
 * @author ob, scs
 */
object UserController extends Controller {

  /**
   * Form object for user data.
   */
  val userForm = Form(
    mapping(
      "Name" -> text)(CreateUserForm.apply)(CreateUserForm.unapply))

  /**
   * Adds a new user with the given data to the system.
   *
   * @return welcome page for new user
   */
  def addUser : Action[AnyContent] = Action { implicit request =>
    userForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.index(formWithErrors))
      },
      userData => {
        val newUser = services.UserService.addUser(userData.name)
        Redirect(routes.UserController.welcomeUser(newUser.name)).
          flashing("success" -> "User saved!")
      })
  }

  /**
   * Shows the welcome view for a newly registered user.
   */
  def welcomeUser(username: String) : Action[AnyContent] = Action {
    Ok(views.html.welcomeUser(username))
  }

  /**
   * List all users currently available in the system.
   */
  def showUsers : Action[AnyContent] = Action {
    Ok(views.html.users(UserService.registeredUsers))
  }

}
