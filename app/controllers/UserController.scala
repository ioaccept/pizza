package controllers

import play.api.mvc.{Action, AnyContent, Controller}
import play.api.data.Form
import play.api.data.Forms._
import forms.CreateUserForm
import services.UserService


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
      "Name" -> text,
      "Password" -> nonEmptyText,
      "Distance" -> number(min = 0, max = 20),
      "Admin" -> text
    )
    (CreateUserForm.apply)(CreateUserForm.unapply))

  /**
    * Adds a new user with the given data to the system.
    *
    * @return welcome page for new user
    */
  def addUser: Action[AnyContent] = Action { implicit request =>
    userForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.register(formWithErrors))
      },
      userData => {
        val user = UserService.registeredUsers.find {
          _.name == userData.name
        }.isDefined
        if (user) {
          Redirect(routes.Application.index())
        } else {
          val newUser = services.UserService.addUser(userData.name, userData.password, userData.distance, userData.admin)
          Redirect(routes.UserController.welcomeUser(newUser.name)).
            flashing("success" -> "User saved!")
        }
      })
  }

  /**
    * Change the data from existing User
    *
    * @return a Page ????
    */
  def changeUser: Action[AnyContent] = Action { implicit request =>
    userForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest("NEIN")
      },
      userData => {
        val changeUser = services.UserService.changeUser(userData.name, userData.password, userData.distance, userData.admin)
        Redirect(routes.UserController.welcomeUser(changeUser.name)).
          flashing("success" -> "User saved!")
      })
  }

  /**
    * Shows the welcome view for a newly registered user.
    */
  def welcomeUser(username: String): Action[AnyContent] = Action {
    Ok(views.html.welcomeUser(username))
  }

  /**
    * Shows the reigster page for new Users
    *
    * @return Register page for new Users
    */

  def registerUser: Action[AnyContent] = Action {
    Ok(views.html.register(controllers.UserController.userForm))
  }

  /**
    * List all users currently available in the system.
    */
  def showUsers: Action[AnyContent] = Action { request =>
    request.session.get("stuff").map { user =>
      Ok(views.html.customer(UserService.registeredUsers, controllers.UserController.userForm))
    }.getOrElse {
      Unauthorized("NEIN")
    }
  }
}