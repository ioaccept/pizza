package forms


/**
  * Form containing data to create a user.
  *
  * @param name username
  * @param distance
  * @param password
  */
case class CreateUserForm(name: String, password: String, distance: Int, admin: String)
