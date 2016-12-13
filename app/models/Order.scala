package models

/**
  * Order
  *
  * @param id
  * @param orderName
  * @param orderItem
  * @param orderExtras
  * @param orderQuantity
  * @param orderSize
  * @param orderPrice
  * @param time
  */
case class Order(var id: Long, var orderName: String, var orderItem: String, var orderExtras: String, var orderQuantity: BigDecimal, var orderSize: BigDecimal, var orderPrice: BigDecimal, var time: BigDecimal)
