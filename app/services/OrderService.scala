package services

import dbaccess.{OrderDao, OrderDaoT}
import models.{Order, OrderPrice}

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
    * @param orderName
    * @param orderItem
    * @param orderQuantity
    * @param orderSize
    * @param orderPrice
    * @param time
    * @return a new Order
    */
  def addOrder(orderName: String, orderItem: String, orderExtra: String, orderQuantity: BigDecimal, orderSize: BigDecimal, orderPrice: BigDecimal, time: BigDecimal): Order = {
    // create User
    val newOrder = Order(-1, orderName, orderItem, orderExtra, orderQuantity, orderSize, orderPrice, time)
    // persist and return User
    orderDao.addOrder(newOrder)
  }

  /**
    * Shows all Orders from User
    *
    * @param username username
    * @return A list of all Orders from User
    */
  def showOrders(username: String): List[Order] = {
    orderDao.showOrders(username)
  }

  /**
    * Shows all Orders
    *
    * @return all Orders
    */
  def showAllOrders: List[Order] = {
    orderDao.showAllOrders
  }

  /**
    * Shows the total turnover from user
    *
    * @param username
    * @return total turnover
    */
  def showTotalPrice(username: String): Option[BigDecimal] = {
    orderDao.showTotalPrice(username)
  }

  /**
    * Show total turnover from all user
    *
    * @return total turnover
    */
  def showTotalAllPrice: Option[BigDecimal] = {
    orderDao.showTotalAllPrice
  }

  /**
    * Show the average turnover from User
    *
    * @param username
    * @return average turnover
    */
  def showAVGPrice(username: String): Option[BigDecimal] = {
    orderDao.showAVGPrice(username)
  }

  /**
    * Show the average turnover from all Users
    *
    * @return average turnover
    */
  def showAVGAllPrice: Option[BigDecimal] = {
    orderDao.showAVGAllPrice
  }
}

object OrderService extends OrderServiceT
