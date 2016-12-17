package dbaccess

import anorm.SQL
import play.api.Play.current
import play.api.db.DB
import models.{Order}


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
        SQL("insert into Orders(name, item, extras, quantity, size, price, time) values (({name}), ({item}), ({extras}), ({quantity}), ({size}), ({price}), ({time}))").on(
          'name -> order.orderName, 'item -> order.orderItem, 'extras -> order.orderExtras, 'quantity -> order.orderQuantity, 'size -> order.orderSize, 'price -> order.orderPrice, 'time -> order.time).executeInsert()
      order.id = orderid.get
    }
    order
  }

  /**
    * Show all Orders from User
    *
    * @param username
    * @return all Orders from User
    */
  def showOrders(username: String): List[Order] = {
    DB.withConnection { implicit c =>
      val selectOrder = SQL("Select * from Orders where name = ({name})").on('name -> username)
      // Transform the resulting Stream[Row] to a List[(String,String)]
      val orders = selectOrder().map(row => Order(row[Long]("id"), row[String]("name"), row[String]("item"), row[String]("extras"),
        row[BigDecimal]("quantity"), row[BigDecimal]("size"), row[BigDecimal]("price"), row[BigDecimal]("time"))).toList
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
      val selectOrder = SQL("Select * from Orders Order by name")
      // Transform the resulting Stream[Row] to a List[(String,String)]
      val orders = selectOrder().map(row => Order(row[Long]("id"), row[String]("name"), row[String]("item"), row[String]("extras"),
        row[BigDecimal]("quantity"), row[BigDecimal]("size"), row[BigDecimal]("price"), row[BigDecimal]("time"))).toList
      orders
    }
  }

  /**
    * Show the total turnover from user
    *
    * @param username
    * @return total turnover
    */
  def showTotalPrice(username: String): Option[BigDecimal] = {
    DB.withConnection { implicit c =>
      val selectPrice = SQL("Select SUM(price) from Orders where name = ({name})").on('name -> username).apply.headOption
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
    * @param username
    * @return average turnover
    */
  def showAVGPrice(username: String): Option[BigDecimal] = {
    DB.withConnection { implicit c =>
      val selectPrice = SQL("Select AVG(price) from Orders where name = ({name})").on('name -> username).apply.headOption
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
