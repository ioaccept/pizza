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
    * @param name
    * @param password
    * @param distance
    * @param admin
    * @return a new User
    */
  def addUser(name: String, password: String, distance: BigDecimal, admin: Boolean): User = {
    // create User
    val newUser = User(-1, name, password, distance, admin)
    // persist and return User
    userDao.addUser(newUser)
  }

  /**
    * Change Data from existing User
    *
    * @return User
    */
  def changeUser(changeUser: User): User = {
    // change Userdata
    val newUser = changeUser
    // persist and return User
    userDao.changeUser(newUser)
  }

  /**
    * Removes a user by id from the system.
    *
    * @param id users id
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
}
object UserService extends UserServiceT
