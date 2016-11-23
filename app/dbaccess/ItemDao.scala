package dbaccess

import anorm.SQL
import play.api.Play.current
import play.api.db.DB
import models.Item


/**
  * Data access object for items related operations.
  *
  * @author ob, scs
  */
trait ItemDaoT {

  /**
    * Returns a list of available items from the database.
    *
    * @return a list of items objects.
    */
  def showItem: List[Item] = {
    DB.withConnection { implicit c =>
      val selectItems = SQL("Select Items.id, Items.name, Items.cat_id, Items.price, Cat.id, Cat.name From Items, Cat where Items.cat_id = Cat.id;")
      // Transform the resulting Stream[Row] to a List[(String,String)]
      val items = selectItems().map(row => Item(row[Long]("Items.id"), row[String]("Cat.name"), row[String]("Items.name"), row[BigDecimal]("Items.price"))).toList
      items
    }
  }

  /**
    * Returns the price
    *
    * @return ???.
    */
  def showPrice(itemName:String) : List[Item] = {
    DB.withConnection { implicit c =>
      val selectItems = SQL("Select price From Items where name = ({name});")on('name -> itemName)
      // Transform the resulting Stream[Row] to a List[(String,String)]
      val items = selectItems().map(row => Item(row[Long]("Items.id"), row[String]("Cat.name"), row[String]("Items.name"), row[BigDecimal]("Items.price"))).toList
      items
    }
  }
}
object ItemDao extends ItemDaoT
