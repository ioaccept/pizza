package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models.User

object Application extends Controller {

  val userForm = Form(
    mapping(
      "Name" -> text)(User.apply)(User.unapply))

  def addUser = Action { implicit request =>
    userForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.index(formWithErrors))
      },
      userData => {
        val newUser = models.User(userData.name)
        Redirect(routes.Application.showUser(newUser.name)).flashing("success" -> "User saved!")
      })
  }

  def showUser(username : String) = Action {
    Ok(views.html.user(username))
  }
  
  def index = Action {
    Ok(views.html.index(userForm))
  }

}