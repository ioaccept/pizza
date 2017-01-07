package services

import dbaccess.{CategoryDao, CategoryDaoT}
import models.Category

/**
  * Service class for user related operations.
  *
  * @author ob, scs
  */
trait CategoryServiceT {

  val categoryDao: CategoryDaoT = CategoryDao

  /**
    * Gets a list of all categorys.
    *
    * @return list of categorys.
    */
  def showCategory: List[Category] = {
    categoryDao.showCategory
  }
}

object CategoryService extends CategoryServiceT
