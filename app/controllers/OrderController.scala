package controllers

import forms.CreateOrderForm
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
  def addOrder(username: String): Action[AnyContent] = Action { implicit request =>
    orderForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest("Falsch")
      },
      userData => {
        val item = ItemService.showItem.find {
          _.name == userData.itemName
        }.head
        val user = UserService.registeredUsers.find {
          _.name == username
        }.head
        val extras = ExtrasService.showExtras.find {
          _.name == userData.extras
        }.head

        val time = user.distance * 2
        val timePizza = user.distance * 2 + 10 * userData.itemQuantity
        val orderPrice = userData.itemQuantity * userData.itemSize * item.price + extras.price

        if (item.catName == "Pizza") {
          services.OrderService.addOrder(username, userData.itemName, userData.extras, userData.itemQuantity, userData.itemSize, orderPrice, timePizza)
        } else {
          services.OrderService.addOrder(username, userData.itemName, userData.extras, userData.itemQuantity, userData.itemSize, orderPrice, time)
        }
        Redirect(routes.OrderController.myOrders()).withSession("user" -> username)
      })
  }

  /**
    * List all Orders from User
    *
    * @return a page of all Orders from User, when it failed return NEIN
    */
  def myOrders: Action[AnyContent] = Action { request =>
    request.session.get("user").map { user =>
      Ok(views.html.myOrder(user, OrderService.showOrders(user), OrderService.showTotalPrice(user), OrderService.showAVGPrice(user)))
    }.getOrElse {
      Unauthorized("NEIN")
    }
  }

  def userOrders(username: String): Action[AnyContent] = Action { request =>
    request.session.get("stuff").map { user =>
      Ok(views.html.myOrder(username, OrderService.showOrders(username), OrderService.showTotalPrice(username), OrderService.showAVGPrice(username)))
    }.getOrElse {
      Unauthorized("NEIN")
    }
  }

  /**
    * All Orders
    *
    * @return all Orders
    */
  def allOrders: Action[AnyContent] = Action { request =>
    request.session.get("stuff").map { user =>
      Ok(views.html.allOrder(UserService.registeredUsers, OrderService.showAllOrders, OrderService.showTotalAllPrice, OrderService.showAVGAllPrice))
    }.getOrElse {
      Unauthorized("NEIN")
    }
  }
}