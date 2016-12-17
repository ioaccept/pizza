package forms

/**
  * Form containing data to create a user.
  *
  * @param name username
  * @param cat
  * @param price
  */
case class CreateItemForm(name: String, cat: Int, price: BigDecimal, active: Boolean)
