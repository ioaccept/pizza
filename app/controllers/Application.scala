package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models._

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
        val newUser = models.Users.addUser(userData.name)
        Redirect(routes.Application.welcomeUser(newUser.name)).
          flashing("success" -> "User saved!")
      })
  }

  def welcomeUser(username: String) = Action {
    Ok(views.html.welcomeUser(username))
  }

  def showUsers = Action {
    Ok(views.html.users(Users.registeredUsers))
  }

  def index = Action {
    Ok(views.html.index(userForm))
  }

}