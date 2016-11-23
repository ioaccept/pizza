package services

import dbaccess.{UserDao, UserDaoT}
import models.User

/**
  * Service class for user related operations.
  *
  * @author ob, scs
  */
trait UserServiceT {

  val userDao: UserDaoT = UserDao

  /**
    * Adds a new user to the system.
    *
    * @param name name of the new user.
    * @return the new user.
    */
  def addUser(name: String): User = {
    // create User
    val newUser = User(-1, name)
    // persist and return User
    userDao.addUser(newUser)
  }

  /**
    * Removes a user by id from the system.
    *
    * @param id users id.
    * @return a boolean success flag.
    */
  def rmUser(id: Long): Boolean = userDao.rmUser(id)





  /**
    * Gets a list of all registered users.
    *
    * @return list of users.
    */
  def registeredUsers: List[User] = {
    userDao.registeredUsers
  }

  /**
    * Get a Option with the name from the user
    *
    * @return name from the user
    */
  def logUser(name: String): Option[String] = {
    userDao.logUser(name)
  }
}

  object UserService extends UserServiceT
