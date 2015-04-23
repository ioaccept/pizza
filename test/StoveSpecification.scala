import models.{Stove, Pizza}
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import org.specs2.ScalaCheck

@RunWith(classOf[JUnitRunner])
class StoveSpecification extends Specification with ScalaCheck {

  "The stove" should {

    "return three pizza at once if the capacity is 4" in {
      val stove = new Stove(4)
      val listOfPizza = List(new Pizza, new Pizza, new Pizza)
      stove += listOfPizza
      stove.next().length must_== 3
      stove.next() must beEmpty
    }

    "return three pizza in two parts if the capacity is 2" in {
      val stove = new Stove(2)
      val listOfPizza = List(new Pizza, new Pizza, new Pizza)
      stove += listOfPizza
      stove.next().length must_== 2
      stove.next().length must_== 1
      stove.next() must beEmpty
    }

    "returns pizza in a correct way" in {
      prop { (capacity: Int, noOfP: Int) =>

        var capa = Math.abs(capacity % 10)
        if (capa == 0) capa = 1
        var noOfPizza = Math.abs(noOfP % 50)
        if (noOfPizza == 0) noOfPizza = 25

        val stove = new Stove(capa)
        stove += List.fill(noOfPizza)(new Pizza)

        var returnedPizza = 0
        var noOfNext = -1
        var nextPizza: Int = 0
        do {
          nextPizza = stove.next().length
          noOfNext += 1
          returnedPizza += nextPizza
        } while (nextPizza != 0)

        returnedPizza must_== noOfPizza
        noOfNext must_== (noOfPizza / capa + (if (noOfPizza % capa > 0) 1 else 0))
      }
    }

  }

}
