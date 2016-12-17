package controllers

import forms.CreateOrderForm
import models.Order
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, AnyContent, Controller}
import services.{ExtrasService, ItemService, OrderService, UserService}

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
      "ItemQuantity" -> number,
      "Extras" -> text
    )(CreateOrderForm.apply)(CreateOrderForm.unapply))


  /**
    * Add Order
    *
    * @return page of all myOrder
    */
  def addOrder: Action[AnyContent] = Action { implicit request =>
    orderForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest("AddOrder")
      },
      userData => {
        val item = ItemService.showItem.find {
          _.name == userData.itemName
        }.head

        val extras = ExtrasService.showExtras.find {
          _.name == userData.extras
        }.head


        val myOrder = new Order(-1, request.session.get("id").get.toLong,
          request.session.get("user").get, request.session.get("distance").get.toLong,
          item.name, extras.name, extras.price, userData.itemQuantity, userData.itemSize,
          item.price, null, null)

        if (item.catName == "Pizza") {
          myOrder.getOrderPriceWithPizza()
          myOrder.deliveryTimeWithPizza
          services.OrderService.addOrder(myOrder)
        } else {
          myOrder.getOrderPrice()
          myOrder.deliveryTime
          services.OrderService.addOrder(myOrder)
        }
        Redirect(routes.OrderController.myOrders())
      })
  }

  /**
    * List all Orders from User
    *
    * @return a page of all Orders from User, when it failed return NEIN
    */
  def myOrders: Action[AnyContent] = Action { request =>
    val connected = request.session.get("customer").isDefined
    if (connected) {
      val userId = request.session.get("id").get.toLong
      val userName = request.session.get("user").get.toString
      Ok(views.html.myOrder(userName, OrderService.showOrders(userId), OrderService.showTotalPrice(userId), OrderService.showAVGPrice(userId)))
    } else {
      Unauthorized("myOrders")
    }
  }

  def userOrders(username: String): Action[AnyContent] = Action { request =>
    val connected = request.session.get("staff").isDefined
    if (connected) {
      val user = UserService.registeredUsers.find {
        _.name == username
      }.head
      Ok(views.html.myOrder(username, OrderService.showOrders(user.id), OrderService.showTotalPrice(user.id), OrderService.showAVGPrice(user.id)))
    } else {
      Unauthorized("UserOrders")
    }
  }

  /**
    * All Orders
    *
    * @return all Orders
    */
  def allOrders: Action[AnyContent] = Action { request =>
    val connected = request.session.get("staff").isDefined
    if (connected) {
      Ok(views.html.allOrder(UserService.registeredUsers, OrderService.showAllOrders, OrderService.showTotalAllPrice, OrderService.showAVGAllPrice))
    } else{
      Unauthorized("AllOrders")
    }
  }
}