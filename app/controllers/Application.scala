package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models._

/**
 * Main controller of the Pizza Service application.
 * 
 * @author ob, scs
 */
object Application extends Controller {

  /**
   * Shows the start page of the application.
   */
  def index = Action {
    Ok(views.html.index(controllers.UserController.userForm))
  }

}