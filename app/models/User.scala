package models

/**
  * User entity.
  *
  * @param id database id of the user.
  * @param name name of the user.
  * @param password password of the user
  * @param distance distance to company
  * @param admin admin flag of user
  */
case class User(var id: Long, var name: String, var password: String, var distance: BigDecimal, var admin: Boolean)
