package dbaccess

import anorm.SQL
import play.api.Play.current
import play.api.db.DB
import models.{Item, Order}


/**
  * Data access object for user related operations.
  *
  * @author ob, scs
  */
trait OrderDaoT {

  /**
    * Create a Order
    *
    * @param order
    * @return new Order
    */
  def addOrder(order: Order): Order = {
    DB.withConnection { implicit c =>
      val orderid: Option[Long] =
        SQL("insert into Orders(name, item, quantity, size, price) values (({name}), ({item}), ({quantity}), ({size}), ({price}))").on(
          'name -> order.orderName, 'item -> order.orderItem, 'quantity -> order.orderQuantity, 'size -> order.orderSize, 'price -> order.orderPrice).executeInsert()
      order.id = orderid.get
    }
    order
  }

  /**
    * Show all Order
    *
    * @param orderName
    * @return A list of all Orders
    */
  def showOrders(orderName: String): List[Order] = {
    DB.withConnection { implicit c =>
      val selectOrder = SQL("Select * from Orders where name = ({name})").on('name -> orderName)
      // Transform the resulting Stream[Row] to a List[(String,String)]
      val orders = selectOrder().map(row => Order(row[Long]("id"), row[String]("name"), row[String]("item"), row[BigDecimal]("quantity"), row[BigDecimal]("size"), row[BigDecimal]("price"))).toList
      orders
    }
  }
}
object OrderDao extends OrderDaoT
