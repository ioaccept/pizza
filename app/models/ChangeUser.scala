package models

/**
  * ???
  * @param name
  * @param password
  * @param distance
  * @param admin
  */
case class ChangeUser(var name: String, var password: String, var distance: BigDecimal, var admin: String)
