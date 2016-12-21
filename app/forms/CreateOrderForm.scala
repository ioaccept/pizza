package forms

/**
  * Form containing data to create a order.
  *
  * @param itemName
  */
case class CreateOrderForm(itemName: String, itemSize: Int, itemQuantity: Int, extras: String)
