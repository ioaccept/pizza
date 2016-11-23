package controllers

import forms.LoginUserForm
import play.api.mvc.{Action, AnyContent, Controller}
import play.api.data.Form
import play.api.data.Forms.{mapping, text}
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
      "Name" -> text)
    (LoginUserForm.apply)(LoginUserForm.unapply))

  /**
    * List all users currently available in the system.
    */
  def showUsers: Action[AnyContent] = Action {
    Ok(views.html.users(UserService.registeredUsers))
  }

  /**
    * Shows the view for a registered User
    */
  def loginUser(username: String): Action[AnyContent] = Action {
    Ok(views.html.loginUser(username, ItemService.showItem, ExtrasService.showExtras, controllers.OrderController.orderForm))
  }

  /**
    * Shows the view for a registered User
    */
  def loginStuff(username: String): Action[AnyContent] = Action {
    Ok(views.html.loginStuff(username))
  }
  /**
    * Search a User
    *
    * @return page for registered User
    */
def showUser: Action[AnyContent] = Action { implicit request =>
  loginForm.bindFromRequest.fold(
    formWithErrors => {
      BadRequest(views.html.index(controllers.LoginController.loginForm))
    },
    userData => {
      val getUser = UserService.registeredUsers.find {
        _.name == userData.name
      }.headOption.get.name
      val getRole = UserService.registeredUsers.find {
        _.name == userData.name
      }.headOption.get.admin

          if (getRole == "nein") {
            Redirect(routes.LoginController.loginUser(getUser))
          } else {
            Redirect(routes.LoginController.loginStuff(getUser))
          }
    })
}
}
