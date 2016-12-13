package dbaccess

import anorm.SQL
import play.api.Play.current
import play.api.db.DB
import anorm.NamedParameter.symbol
import models.{ChangeUser, User}


/**
  * Data access object for user related operations.
  *
  * @author ob, scs
  */
trait UserDaoT {

  /**
    * Creates the given user in the database.
    *
    * @param user the user object to be stored.
    * @return the persisted user object
    */
  def addUser(user: User): User = {
    DB.withConnection { implicit c =>
      val id: Option[Long] =
        SQL("insert into Users(name, password, distance, admin) values (({name}), ({password}), ({distance}), ({admin}))").on(
          'name -> user.name, 'password -> user.password, 'distance -> user.distance, 'admin -> user.admin).executeInsert()
      user.id = id.get
    }
    user
  }

  /**
    * Creates the given changeUser in the database.
    *
    * @param changeUser the changeUser object to be changed
    * @return the changed User
    */
  def changeUser(changeUser: ChangeUser): ChangeUser = {
    DB.withConnection { implicit c =>
      val change =
        SQL("update Users SET password = ({password}), distance = ({distance}), admin = ({admin}) where name = ({name})").on(
          'password -> changeUser.password, 'distance -> changeUser.distance, 'admin -> changeUser.admin, 'name -> changeUser.name).executeUpdate()
    }
    changeUser
  }

  /**
    * Removes a user by id from the database.
    *
    * @param id the users id
    * @return a boolean success flag
    */
  def rmUser(id: Long): Boolean = {
    DB.withConnection { implicit c =>
      val rowsCount = SQL("delete from Users where id = ({id})").on('id -> id).executeUpdate()
      rowsCount > 0
    }
  }

  /**
    * Returns a list of available user from the database.
    *
    * @return a list of user objects.
    */
  def registeredUsers: List[User] = {
    DB.withConnection { implicit c =>
      val selectUsers = SQL("Select * from Users;")
      // Transform the resulting Stream[Row] to a List[(Long, String, String, BigDecimal, String)]
      val users = selectUsers().map(row => User(row[Long]("id"), row[String]("name"), row[String]("password"), row[BigDecimal]("distance"), row[String]("admin"))).toList
      users
    }
  }
}

object UserDao extends UserDaoT
