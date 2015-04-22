package models

import scala.collection.immutable.Queue

class Stove(capacity: Int = 1) {

  private var queue = Queue[List[Pizza]]()

  def +=(pizza: Pizza): Stove = {
    if (queue.isEmpty) {
      queue = Queue(List(pizza))
    } else {
      val last: List[Pizza] = queue.last
      queue = if (last.length < capacity) {
        queue.init :+ (pizza :: last)
      } else {
        queue :+ List(pizza)
      }
    }
    this
  }

  def +=(listOfPizza: List[Pizza]): Stove =
    listOfPizza.foldLeft(this) { (stove, pizza) => stove += pizza }

  def next(): List[Pizza] =
    if (queue.isEmpty) {
      List()
    } else {
      val first = queue.head
      queue = queue.tail
      first
    }

}
