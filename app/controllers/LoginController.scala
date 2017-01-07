package controllers

import forms.LoginUserForm
import models.User
import play.api.mvc.{Action, AnyContent, Controller}
import play.api.data.Form
import play.api.data.Forms._
import services.{ExtrasService, ItemService, UserService}


/**
  * Controller for user specific operations.
  *
  * @author ob, scs
  */
object LoginController extends Controller {

  /**
    * Form object for user data.
    */
  val loginForm = Form(
    mapping(
      "Name" -> nonEmptyText,
      "Password" -> nonEmptyText)
    (LoginUserForm.apply)(LoginUserForm.unapply))


  /**
    * Show the view for Customer
    *
    * @return login page for Customer
    */
  def loginUser: Action[AnyContent] = Action { request =>
    val connected = request.session.get("customer").isDefined
    if (connected) {
      val user = User(request.session.get("id").get.toLong, request.session.get("user").get.toString, null, request.session.get("distance").get.toLong, false, true)
      Ok(views.html.loginUser(user, ItemService.showActiveItem, ExtrasService.showExtras, controllers.OrderController.orderForm))
    } else {
      Unauthorized("LoginUser")
    }
  }

  /**
    * Show the view for registerd Stuff
    *
    * @return login page for Staff
    */
  def loginStaff: Action[AnyContent] = Action { request =>
    val connected = request.session.get("staff").isDefined
    if (connected) {
      val user = User(request.session.get("id").get.toLong, request.session.get("user").get.toString, null, null, true, true)
      Ok(views.html.loginStaff(user))
    } else {
      Unauthorized("LoginStaff")
    }
  }

  /**
    * User logout
    *
    * @return the index page
    */

  def logout: Action[AnyContent] = Action {
    Ok(views.html.index(loginForm)).withNewSession
  }

  /**
    * Search a User
    *
    * @return login page
    */
  def searchUser: Action[AnyContent] = Action {
    implicit request =>
      loginForm.bindFromRequest.fold(
        formWithErrors => {
          BadRequest(views.html.index(formWithErrors))
        },
        userData => {
          val exists = UserService.registeredUsers.exists(_.name == userData.name)

          if (exists) {
            val user = UserService.registeredUsers.find {
              _.name == userData.name
            }.head

            if (user.password == userData.password && user.active) {
              if (!user.admin) {
                Redirect(routes.LoginController.loginUser()) withSession("id" -> user.id.toString, "user" -> user.name, "distance" -> user.distance.toString, "customer" -> "yes")
              } else {
                Redirect(routes.LoginController.loginStaff()) withSession("id" -> user.id.toString, "user" -> user.name, "staff" -> "yes")
              }
            } else Redirect(routes.Application.index())

          } else Redirect(routes.Application.index())

        }
      )
  }


}
