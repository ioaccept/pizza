package services

import dbaccess.{OrderDao, OrderDaoT}
import models.Order

/**
  * Service class for order related operations.
  *
  * @author ob, scs
  */
trait OrderServiceT {

  val orderDao: OrderDaoT = OrderDao

  /**
    * Add a new Order
    *
    * @param orderName, orderItem
    * @return new Order
    */
  def addOrder(orderName: String, orderItem: String, orderQuantity: BigDecimal, orderSize: BigDecimal, orderPrice: BigDecimal): Order = {
    // create User
    val newOrder = Order(-1, orderName, orderItem, orderQuantity, orderSize, orderPrice)
    // persist and return User
    orderDao.addOrder(newOrder)
  }

  /***
    * Shows all Orders
    *
    * @param name username
    * @return A lsit of all Orders
    */
  def showOrders(name: String) : List[Order] = {
    orderDao.showOrders(name)
  }
}

  object OrderService extends OrderServiceT
