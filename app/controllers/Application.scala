package controllers

import play.api.mvc.{Action, Controller, AnyContent}

/**
  * Main controller of the Pizza Service application.
  *
  * @author ob, scs
  */
object Application extends Controller {

  /**
    * Shows the start page of the application.
    *
    * @return main web page
    */
  def index: Action[AnyContent] = Action { request =>
    if (request.session.get("customer").isDefined) {
      Redirect(routes.LoginController.loginUser())
    } else if (request.session.get("staff").isDefined) {
      Redirect(routes.LoginController.loginStaff())
    } else {
      Ok(views.html.index(controllers.LoginController.loginForm))
    }
  }
}
