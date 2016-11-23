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
object OrderController extends Controller {

  /**
    * Form object for order data.
    */
  val orderForm = Form(
    mapping(
      "ItemName" -> text,
      "ItemSize" -> number,
      "ItemQuantity" -> number
    )(CreateOrderForm.apply)(CreateOrderForm.unapply))

  /**
    * Add Order
    *
    * @return page of all myOrder
    */
  def addOrder(username: String): Action[AnyContent] = Action { implicit request =>
    orderForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.fehler(formWithErrors))
      },
      userData => {
        val orderPrice = userData.itemQuantity * userData.itemSize * 0.5
        services.OrderService.addOrder(username, userData.itemName, userData.itemQuantity, userData.itemSize, orderPrice)
        Redirect(routes.OrderController.showOrders(username)).
          flashing("success" -> "Order saved!")
      })
  }

  /**
    * List all orders
    */
  def showOrders(name: String): Action[AnyContent] = Action {
    Ok(views.html.myOrder(OrderService.showOrders(name)))
  }
}