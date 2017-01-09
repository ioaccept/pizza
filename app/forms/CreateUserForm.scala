package forms


/**
  * Form containing data to create a user.
  *
  * @param name     username
  * @param distance to the company
  * @param password from the user
  */
case class CreateUserForm(name: String, password: String, distance: BigDecimal, admin: Boolean, activ: Boolean)
