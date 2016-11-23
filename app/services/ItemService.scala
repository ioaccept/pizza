package services

import dbaccess.{ItemDao, ItemDaoT}
import models.Item

/**
  * Service class for user related operations.
  *
  * @author ob, scs
  */
trait ItemServiceT {

  val itemDao: ItemDaoT = ItemDao

  /**
    * Gets a list of all registered users.
    *
    * @return list of users.
    */
  def showItem: List[Item] = {
    itemDao.showItem
  }

}

object ItemService extends ItemServiceT
