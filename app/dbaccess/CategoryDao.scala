package dbaccess
import anorm.SQL
import play.api.Play.current
import play.api.db.DB
import models.Category


/**
  * Data access object for items related operations.
  *
  * @author ob, scs
  */
trait CategoryDaoT {

  /**
    * Returns a list of available extras from the database.
    *
    * @return a list of items objects.
    */
  def showCategory: List[Category] = {
    DB.withConnection { implicit c =>
      val selectCategory = SQL("Select * From Cat;")
      // Transform the resulting Stream[Row] to a List[(String,String)]
      val category = selectCategory().map(row => Category(row[Long]("id"), row[String]("name"))).toList
      category
    }
  }
}
object CategoryDao extends CategoryDaoT
