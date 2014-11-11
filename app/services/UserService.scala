package services

import dbaccess.UserDao
import models.User

/**
 * Service class for user related operations.
 * 
 * @author ob, scs
 */
object UserService {
  
  /**
   * Adds a new user to the system.
   * @param name name of the new user.
   * @return the new user.
   */
  def addUser(name: String): User = {
    // create User
    val newUser = User(-1, name)
    // persist and return User
    return UserDao.addUser(newUser)   
  }
  
  /**
   * Gets a list of all registered users.
   * @return list of users.
   */
  def registeredUsers: List[User] = {
    return UserDao.registeredUsers
  }

}