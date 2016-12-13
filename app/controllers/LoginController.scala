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
    * Show the view for registered User
    *
    * @return the view for registerd User
    */
  def loginUser: Action[AnyContent] = Action { request =>
    request.session.get("user").map { user =>
      Ok(views.html.loginUser(user, ItemService.showItem, ExtrasService.showExtras, controllers.OrderController.orderForm))
    }.getOrElse {
      Unauthorized("NEIN")
    }
  }

  /**
    * Show the view for registerd Stuff
    *
    * @return the view for registerd Stuff
    */
  def loginStuff: Action[AnyContent] = Action { request =>
    request.session.get("stuff").map { user =>
      Ok(views.html.loginStuff(user))
    }.getOrElse {
      Unauthorized("NEIN")
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
    * @return page for registered User
    */
  def searchUser: Action[AnyContent] = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.index(formWithErrors))
      },
      userData => {
        val user = UserService.registeredUsers.find {
          _.name == userData.name
        }.head

        if (user.password == userData.password) {
          if (user.admin == "nein") {
            Redirect(routes.LoginController.loginUser()) withSession ("user" -> user.name)
          } else {
            Redirect(routes.LoginController.loginStuff()) withSession ("stuff" -> user.name)
          }
        } else Redirect(routes.Application.index())


      }
    )
  }


}
