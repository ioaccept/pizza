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
      "Price" -> number
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
        BadRequest("NEIN")
      },
      userData => {
        val item = ItemService.showItem.find {
          _.name == userData.name
        }.isDefined

        if (item) {
          Redirect(routes.ItemController.showItem())
        } else {
          services.ItemService.addItem(userData.name, userData.cat, userData.price)
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
        BadRequest("NEIN")
      },
      userData => {
        val changeItem = ItemService.changeItem(userData.name, userData.cat, userData.price)
        Redirect(routes.ItemController.showItem()).
          flashing("success" -> "User saved!")
      })
  }
  /**
    * Delete a item from system
    *
    * @return?????????????
    */
  def deleteItem: Action[AnyContent] = Action { implicit request =>
    itemForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest("NEIN")
      },
      userData => {
        val item = ItemService.showItem.find {
          _.name == userData.name
        }.isDefined
        val orderedItem = OrderService.showAllOrders.find {
          _.orderItem == userData.name
        }.isDefined

        if (item && !orderedItem) {
          ItemService.rmItem(userData.name)
          Redirect(routes.ItemController.showItem()).flashing("success" -> "User deleted!")
        } else {
          Redirect(routes.ItemController.showItem()).flashing("error" -> "User not deleted!")
        }
      }
    )
  }

  def showItem: Action[AnyContent] = Action {
    Ok(views.html.sortiment(ItemService.showItem, ExtrasService.showExtras, CategoryService.showCategory))
  }
}
