package dbaccess

import java.util.Date

import anorm.SQL
import play.api.Play.current
import play.api.db.DB
import models.Order

/**
  * Data access object for user related operations.
  *
  * @author ob, scs
  */
trait OrderDaoT {

  /**
    * Create a new Order
    *
    * @param order
    * @return new Order
    */
  def addOrder(order: Order): Order = {
    DB.withConnection { implicit c =>
      val orderid: Option[Long] =
        SQL("insert into Orders(userId, distance, item, extras, quantity, size, price, ordertime, time) values (({userId}), ({distance}), ({item}), ({extras}), ({quantity}), ({size}), ({price}), ({ordertime}), CURRENT_TIMESTAMP)").on(
          'userId -> order.userId, 'distance -> order.orderDistance, 'item -> order.orderItem, 'extras -> order.orderExtras, 'quantity -> order.orderQuantity, 'size -> order.orderSize, 'price -> order.orderPrice, 'ordertime -> order.delivery).executeInsert()
      order.id = orderid.get
    }
    order
  }

  /**
    * Show all Orders from User
    *
    * @param userId
    * @return all Orders from User
    */
  def showOrders(userId: Long): List[Order] = {
    DB.withConnection { implicit c =>
      val selectOrder = SQL("Select * from Orders, Users where Users.id = userId AND userId = ({userId}) ").on('userId -> userId)
      // Transform the resulting Stream[Row] to a List[(String,String)]
      val orders = selectOrder().map(row => Order(row[Long]("id"), row[BigDecimal]("userId"), row[String]("name"), row[BigDecimal]("distance"), row[String]("item"), row[String]("extras"),
        row[BigDecimal]("quantity"), row[BigDecimal]("size"), row[BigDecimal]("price"), row[BigDecimal]("ordertime"), row[Date]("time"))).toList
      orders
    }
  }

  /**
    * Show all Orders
    *
    * @return all Orders
    */
  def showAllOrders: List[Order] = {
    DB.withConnection { implicit c =>
      val selectOrder = SQL("Select * from Orders, Users where Users.id = userId Order by userId")
      // Transform the resulting Stream[Row] to a List[(String,String)]
      val orders = selectOrder().map(row => Order(row[Long]("id"), row[BigDecimal]("userId"), row[String]("name"), row[BigDecimal]("distance"), row[String]("item"), row[String]("extras"),
        row[BigDecimal]("quantity"), row[BigDecimal]("size"), row[BigDecimal]("price"), row[BigDecimal]("ordertime"), row[Date]("time"))).toList
      orders
    }
  }

  /**
    * Show the total turnover from user
    *
    * @param userId
    * @return total turnover
    */
  def showTotalPrice(userId: Long): Option[BigDecimal] = {
    DB.withConnection { implicit c =>
      val selectPrice = SQL("Select SUM(price) from Orders where userId = ({userId})").on('userId -> userId).apply.headOption
      selectPrice match {
        case Some(row) => Some(row[BigDecimal]("SUM(price)"))
        case None => None
      }
    }
  }

  /**
    * Show the total turnover from all Users
    *
    * @return total turnover
    */
  def showTotalAllPrice: Option[BigDecimal] = {
    DB.withConnection { implicit c =>
      val selectPrice = SQL("Select SUM(price) from Orders").apply.headOption
      selectPrice match {
        case Some(row) => Some(row[BigDecimal]("SUM(price)"))
        case None => None
      }
    }
  }

  /**
    * Show the average turnover from user
    *
    * @param userId
    * @return average turnover
    */
  def showAVGPrice(userId: Long): Option[BigDecimal] = {
    DB.withConnection { implicit c =>
      val selectPrice = SQL("Select AVG(price) from Orders where userId = ({userId})").on('userId -> userId).apply.headOption
      selectPrice match {
        case Some(row) => Some(row[BigDecimal]("AVG(price)"))
        case None => None
      }
    }
  }

  /**
    * Show average Price from all Users
    *
    * @return average Price
    */
  def showAVGAllPrice: Option[BigDecimal] = {
    DB.withConnection { implicit c =>
      val selectPrice = SQL("Select AVG(price) from Orders").apply.headOption
      selectPrice match {
        case Some(row) => Some(row[BigDecimal]("AVG(price)"))
        case None => None
      }
    }
  }
}

object OrderDao extends OrderDaoT
