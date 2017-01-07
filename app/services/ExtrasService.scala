package services

import dbaccess.{ExtrasDao, ExtrasDaoT}
import models.Extras

/**
  * Service class for user related operations.
  *
  * @author ob, scs
  */
trait ExtrasServiceT {

  val extrasDao: ExtrasDaoT = ExtrasDao

  /**
    * Gets a list of all extras.
    *
    * @return list of extras.
    */
  def showExtras: List[Extras] = {
    extrasDao.showExtra
  }
}

object ExtrasService extends ExtrasServiceT
