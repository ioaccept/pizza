package models

/**
  * Item entity.
  *
  * @param id
  * @param catName
  * @param name
  * @param price
  */
case class Item(var id: Long, var catName: String, var name: String, var price: BigDecimal, var active: Boolean)

