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
    * @return a new Order
    */
  def addOrder(myOrder: Order): Order = {
    // create User
    val newOrder = myOrder
    // persist and return User
    orderDao.addOrder(newOrder)
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
    * Shows all Orders from User
    *
    * @param userId userId
    * @return A list of all Orders from User
    */
  def showOrders(userId: Long): List[Order] = {
    orderDao.showOrders(userId)
  }

  /**
    * Shows the total turnover from user
    *
    * @param userId
    * @return total turnover
    */
  def showTotalPrice(userId: Long): Option[BigDecimal] = {
    orderDao.showTotalPrice(userId)
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
    * @param userId
    * @return average turnover
    */
  def showAVGPrice(userId: Long): Option[BigDecimal] = {
    orderDao.showAVGPrice(userId)
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
