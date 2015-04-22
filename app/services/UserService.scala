package services

import dbaccess.{UserDaoT, UserDao}
import models.User

/**
 * Service class for user related operations.
 *
 * @author ob, scs
 */
trait UserServiceT {

  val userDao : UserDaoT = UserDao

  /**
   * Adds a new user to the system.
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
   * Gets a list of all registered users.
   * @return list of users.
   */
  def registeredUsers: List[User] = {
    userDao.registeredUsers
  }

}

object UserService extends UserServiceT
