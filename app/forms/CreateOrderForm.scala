package forms

/**
  * Form containing data to create a order.
  *
  * @param itemName
  * @param itemSize
  * @param itemQuantity
  * @param extras
  */
case class CreateOrderForm(itemName: String, itemSize: Int, itemQuantity: Int, extras: String)
