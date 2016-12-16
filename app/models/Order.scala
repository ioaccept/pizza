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
  * @param orderExtras
  * @param orderQuantity
  * @param orderSize
  * @param orderPrice
  * @param delivery
  */
case class Order(var id: Long, var userId: BigDecimal, var userName: String, var orderDistance: BigDecimal, var orderItem: String,
                 var orderExtras: String, var orderQuantity: BigDecimal, var orderSize: BigDecimal,
                 var orderPrice: BigDecimal, var delivery: BigDecimal, var date: Date) {

  def getOrderPrice(): Unit ={
    this.orderPrice = orderQuantity * orderSize * orderPrice + 0.5
  }

  def deliveryTime: Unit = {
    this.delivery = orderDistance * 2
  }

  def deliveryTimePizza: Unit = {
    this delivery = orderDistance * 2 + 10 * orderQuantity
  }
}
