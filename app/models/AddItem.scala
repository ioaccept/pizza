package models

/**
  * AddItem entity.
  *
  * @param id
  * @param name
  * @param cat
  * @param price
  */
case class AddItem(var id: Long, var name: String, var cat: BigDecimal, var price: BigDecimal, var active: Boolean)

