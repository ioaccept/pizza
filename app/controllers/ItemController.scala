package controllers

import forms.CreateItemForm
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, AnyContent, Controller}
import services.{CategoryService, ExtrasService, ItemService, OrderService}


/**
  * Controller for items specific operations.
  *
  * @author ob, scs
  */
object ItemController extends Controller {
  /**
    * Form object for user data.
    */
  val itemForm = Form(
    mapping(
      "Name" -> nonEmptyText,
      "Category" -> number,
      "Price" -> bigDecimal,
      "Active" -> boolean
    )
    (CreateItemForm.apply)(CreateItemForm.unapply))

  /**
    * Add a new item to the system
    *
    * @return ??????????????????
    */
  def addItem: Action[AnyContent] = Action { implicit request =>
    itemForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest("AddItem")
      },
      userData => {
        val item = ItemService.showItem.exists(_.name == userData.name)

        if (item) {
          Redirect(routes.ItemController.showItem())
        } else {
          services.ItemService.addItem(userData.name, userData.cat, userData.price, userData.active)
          Redirect(routes.ItemController.showItem()).
            flashing("success" -> "Item saved!")
        }
      })
  }

  /**
    * Change the data from existing Item
    *
    * @return a Page ????????????????????????
    */
  def changeItem: Action[AnyContent] = Action { implicit request =>
    itemForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest("ChangeItem")
      },
      userData => {
        ItemService.changeItem(userData.name, userData.cat, userData.price, userData.active)
        Redirect(routes.ItemController.showItem()).
          flashing("success" -> "User saved!")
      })
  }

  /**
    * Delete a item from system
    *
    * @return ?????????????
    */
  def deleteItem: Action[AnyContent] = Action { implicit request =>
    itemForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest("DeleteItem")
      },
      userData => {
        val item = ItemService.showItem.exists(_.name == userData.name)
        val orderedItem = OrderService.showAllOrders.exists(_.orderItem == userData.name)

        if (item && !orderedItem) {
          ItemService.rmItem(userData.name)
          Redirect(routes.ItemController.showItem()).flashing("success" -> "Item deleted!")
        } else {
          val disableItem = ItemService.showItem.find(
            _.name == userData.name
          ).head
          ItemService.changeItem(disableItem.name, disableItem.id.toInt, disableItem.price, false)
          Redirect(routes.ItemController.showItem()).flashing("error" -> "Item not deleted!")
        }
      }
    )
  }

  def showItem: Action[AnyContent] = Action { request =>
    val connected = request.session.get("staff").isDefined
    if (connected) {
      Ok(views.html.menu(ItemService.showItem, ExtrasService.showExtras, CategoryService.showCategory))
    } else {
      Unauthorized("ShowItem")
    }
  }
}