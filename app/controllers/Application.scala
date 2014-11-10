package controllers

import anorm._
import play.api.Play.current
import play.api._
import play.api.db._
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
        DB.withConnection { implicit c =>
          val id: Option[Long] =
            SQL("insert into User(name) values ({name})").on(
              'name -> userData.name).executeInsert()
        }

        Redirect(routes.Application.showUser(newUser.name)).flashing("success" -> "User saved!")
      })
  }

  def showUser(username: String) = Action {
    Ok(views.html.user(username))
  }

  def showUsers = Action {
    DB.withConnection { implicit c =>
      val selectUsers = SQL("Select name from User;")
      // Transform the resulting Stream[Row] to a List[(String,String)]
      val users = selectUsers().map(row =>
        row[String]("name")).toList
      Ok(views.html.users(users))
    }

  }

  def index = Action {
    Ok(views.html.index(userForm))
  }

}