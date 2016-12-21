package services

import dbaccess.{ItemDao, ItemDaoT}
import models.{Item, AddItem}

/**
  * Service class for user related operations.
  *
  * @author ob, scs
  */
trait ItemServiceT {

  val itemDao: ItemDaoT = ItemDao

  /**
    * Adds a new item to the system.
    *
    * @param name
    * @param cat
    * @param price
    * @return
    */
  def addItem(name: String, cat: Int, price: BigDecimal, active: Boolean): AddItem = {
    // create User
    val newItem = AddItem(-1, name, cat, price, active)
    // persist and return User
    itemDao.addItem(newItem)
  }

  /**
    * Change Data from existing Item
    *
    * @param name
    * @param cat
    * @param price
    * @return
    */
  def changeItem(name: String, cat: Int, price: BigDecimal, active: Boolean): AddItem = {
    // change Userdata
    val changeItem = AddItem(1, name, cat, price, active)
    // persist and return User
    itemDao.changeItem(changeItem)
  }

  /**
    * Delete a item from system
    *
    * @param name username
    * @return a boolean success flag.
    */
  def rmItem(name: String): Boolean = itemDao.rmItem(name)

  /**
    * Gets a list of all items.
    *
    * @return list of users.
    */
  def showItem: List[Item] = {
    itemDao.showItem
  }

  /**
    * Gets a list of items who active.
    *
    * @return list of users.
    */
  def showActiveItem: List[Item] = {
    itemDao.showActiveItem
  }
}

object ItemService extends ItemServiceT
