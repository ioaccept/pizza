package models

/**
 * User entity.
 * @param id
 *
 */
case class Order(var id: Long, var orderName: String, var orderItem: String, var orderQuantity: BigDecimal, var orderSize: BigDecimal, var orderPrice: BigDecimal)
