package models

/**
 * User entity.
 * @param id database id of the user.
 * @param name name of the user.
 */
case class User(var id: Long, var name: String)
