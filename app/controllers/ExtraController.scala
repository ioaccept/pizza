package controllers

import forms.{CreateOrderForm}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, AnyContent, Controller}
import services.OrderService


/**
  * Controller for user specific operations.
  *
  * @author ob, scs
  */
object ExtraController extends Controller {
  /**
    * List all extras
    */
  def showExtrass(name: String): Action[AnyContent] = Action {
    Ok(views.html.myOrder(OrderService.showOrders(name)))
  }
}