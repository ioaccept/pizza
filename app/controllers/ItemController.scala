package controllers

import play.api.mvc.{Action, AnyContent, Controller}
import services.{ExtrasService, ItemService}


/**
  * Controller for items specific operations.
  *
  * @author ob, scs
  */
object ItemController extends Controller {

  def showItem: Action[AnyContent] = Action {
    Ok(views.html.sortiment(ItemService.showItem, ExtrasService.showExtras))
  }
}
