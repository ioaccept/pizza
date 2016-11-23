package models

/**
 * User entity.
 * @param id database id of the user.
 * @param name name of the user.
 */
case class Order(var id: Long, var orderName: String, var orderItem: String, var orderQuantity: BigDecimal, var orderSize: BigDecimal, var orderPrice: BigDecimal)
