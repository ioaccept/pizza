package controllers

import play.api.mvc.{Action, AnyContent, Controller}
import play.api.data.Form
import play.api.data.Forms._
import forms.CreateUserForm
import models.User
import services.{OrderService, UserService}


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
      "Distance" -> bigDecimal,
      "Admin" -> boolean
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
        val user = UserService.registeredUsers.exists(_.name == userData.name)
        if (user) {
          Redirect(routes.Application.index())
        } else {
          if (request.session.get("staff").isDefined) {
            services.UserService.addUser(userData.name, userData.password, userData.distance, userData.admin)
            Redirect(routes.UserController.showUsers()).flashing("success" -> "User saved!")
          } else {
            val newUser = services.UserService.addUser(userData.name, userData.password, userData.distance, userData.admin)
            Redirect(routes.UserController.welcomeUser(newUser.name)).flashing("success" -> "User saved!")
          }
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
        BadRequest("ChangeUser")
      },
      userData => {
        val user = UserService.registeredUsers.exists(_.name == userData.name)

        if (user) {
          val user = UserService.registeredUsers.find(
            _.name == userData.name
          ).head
          val changeUser = new User(user.id, userData.name, userData.password, userData.distance, userData.admin)
          services.UserService.changeUser(changeUser)
          Redirect(routes.UserController.showUsers()).
            flashing("success" -> "User data changed!")
        } else {
          Redirect(routes.UserController.showUsers).flashing("error" -> "User data not changed!")
        }
      })
  }

  /**
    * Delete a User from System
    *
    * @return ???????????????Page
    */
  def deleteUser: Action[AnyContent] = Action { implicit request =>
    userForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest("DeleteUser")
      },
      userData => {
        val user = UserService.registeredUsers.exists(_.name == userData.name)
        if (user) {
          val user = UserService.registeredUsers.find(
            _.name == userData.name
          ).head
          val orderByUser = OrderService.showAllOrders.exists(_.userId == user.id)
          if (!orderByUser) {
            UserService.rmUser(user.id)
            Redirect(routes.UserController.showUsers).flashing("success" -> "User deleted!")
          } else {
            Redirect(routes.UserController.showUsers).flashing("error" -> "User not deleted!")
          }
        } else {
          Redirect(routes.UserController.showUsers).flashing("error" -> "User not deleted!")
        }
      }
    )
  }

  /**
    * List all users currently available in the system.
    */
  def showUsers: Action[AnyContent] = Action { request =>
    request.session.get("staff").map { user =>
      Ok(views.html.customer(UserService.registeredUsers, controllers.UserController.userForm))
    }.getOrElse {
      Unauthorized("ShowUsers")
    }
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
}