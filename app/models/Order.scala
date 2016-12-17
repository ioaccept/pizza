package models

import java.util.Date

/**
  * Order
  *
  * @param id
  * @param userId
  * @param userName
  * @param orderDistance
  * @param orderItem
  * @param extrasPrice
  * @param orderExtras
  * @param orderQuantity
  * @param orderSize
  * @param orderPrice
  * @param delivery
  */
case class Order(var id: Long, var userId: BigDecimal, var userName: String, var orderDistance: BigDecimal,
                 var orderItem: String, var orderExtras: String, var extrasPrice: BigDecimal,
                 var orderQuantity: BigDecimal, var orderSize: BigDecimal, var orderPrice: BigDecimal,
                 var delivery: BigDecimal, var date: Date) {
  /**
    * Calculate the price from Order without Pizza, no Extra can book by User
    */
  def getOrderPrice(): Unit = {
    this.orderExtras = "Keine"
    this.orderPrice = orderQuantity * orderSize * orderPrice
  }
  /**
    * Calculate the price from Order with Pizza
    */
  def getOrderPriceWithPizza(): Unit = {
    this.orderPrice = orderQuantity * orderSize * orderPrice + extrasPrice
  }
  /**
    * Calculate the deliverytime from Order without Pizza
    */
  def deliveryTime: Unit = {
    this.delivery = orderDistance * 2
  }
  /**
    * Calculate the deliverytime from Order with Pizza
    */
  def deliveryTimeWithPizza: Unit = {
    this delivery = orderDistance * 2 + 10 * orderQuantity
  }
}
