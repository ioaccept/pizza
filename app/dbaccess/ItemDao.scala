package dbaccess

import anorm.SQL
import play.api.Play.current
import play.api.db.DB
import models.{AddItem, Item}


/**
  * Data access object for items related operations.
  *
  * @author ob, scs
  */
trait ItemDaoT {

  def addItem(item: AddItem): AddItem = {
    DB.withConnection { implicit c =>
      val id: Option[Long] =
        SQL("insert into Items(name, cat_id, price) values (({name}), ({cat_id}), ({price}))").on(
          'name -> item.name, 'cat_id -> item.cat, 'price -> item.price).executeInsert()
      item.id = id.get
    }
    item
  }

  def changeItem(changeItem: AddItem): AddItem = {
    DB.withConnection { implicit c =>
      val change =
        SQL("update Items SET name = ({name}), cat_id = ({cat_id}), price = ({price}) where name = ({name})").on(
          'name -> changeItem.name, 'cat_id -> changeItem.cat, 'price -> changeItem.price).executeUpdate()
    }
    changeItem
  }
  /**
    * Removes a item by name from the database.
    *
    * @param name the users name
    * @return a boolean success flag
    */
  def rmItem(name: String): Boolean = {
    DB.withConnection { implicit c =>
      val rowsCount = SQL("delete from Items where name = ({name})").on('name -> name).executeUpdate()
      rowsCount > 0
    }
  }
  /**
    * Returns a list of available items from the database.
    *
    * @return a list of items objects.
    */
  def showItem: List[Item] = {
    DB.withConnection { implicit c =>
      val selectItems = SQL("Select Items.id, Items.name, Items.cat_id, Items.price, Cat.id, Cat.name From Items, Cat where Items.cat_id = Cat.id ORDER BY Cat.name;")
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
