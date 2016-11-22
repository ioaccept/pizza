package dbaccess

import anorm.SQL
import play.api.Play.current
import play.api.db.DB
import models.{Extras, Item}


/**
  * Data access object for items related operations.
  *
  * @author ob, scs
  */
trait ExtrasDaoT {

  /**
    * Returns a list of available extras from the database.
    *
    * @return a list of items objects.
    */
  def showExtra: List[Extras] = {
    DB.withConnection { implicit c =>
      val selectExtras = SQL("Select * From Extras;")
      // Transform the resulting Stream[Row] to a List[(String,String)]
      val extras = selectExtras().map(row => Extras(row[Long]("id"), row[String]("name"), row[BigDecimal]("price"))).toList
      extras
    }
  }
}
object ExtrasDao extends ExtrasDaoT
