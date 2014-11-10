package models

import anorm._
import play.api.Play.current
import play.api.db._

object Users {

  def addUser(name: String): User = {
    val newUser = User(name)
    DB.withConnection { implicit c =>
      val id: Option[Long] =
        SQL("insert into User(name) values ({name})").on(
          'name -> name).executeInsert()
    }
    return newUser
  }

  def registeredUsers: List[User] = {
    DB.withConnection { implicit c =>
      val selectUsers = SQL("Select name from User;")
      // Transform the resulting Stream[Row] to a List[(String,String)]
      val usernames = selectUsers().map(row =>
        row[String]("name")).toList
      return usernames map (User(_));
    }
  }

}