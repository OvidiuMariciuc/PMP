package Lab4

import com.cra.figaro.algorithm.factored.VariableElimination
import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling._
import com.cra.figaro.library.atomic.discrete.FromRange
import com.cra.figaro.library.compound.^^

object Ex1 {
  def main(args: Array[String]) {
    //Exercise 3

    val x1 = Flip(0.4)
    val y1 = Flip(0.4)
    val z1 = x1
    val w1 = x1 === z1
    println(VariableElimination.probability(w1, true))

    val x2 = Flip(0.4)
    val y2 = Flip(0.4)
    val z2 = y2
    val w2 = x2 === z2
    println(VariableElimination.probability(w2, true))

    //Exercise 4 - afiseaza 0.05555555555555555
    val die1 = FromRange(1, 7)
    val die2 = FromRange(1, 7)
    val total4 = Apply(die1, die2, (i1: Int, i2: Int) => i1 + i2)
    println(VariableElimination.probability(total4, 11))

    //Exercise 5 - afiseaza 0.4
    val total5 = Apply(die1, die2, (i1: Int, i2: Int) => i1 + i2)
    total5.addCondition((i: Int) => i > 8)
    println(VariableElimination.probability(die1, 6))

    //Exercise 6 - afiseaza 0.004629629629629629
    def doubles = {
      val die1 = FromRange(1, 7)
      val die2 = FromRange(1, 7)
      die1 === die2
    }

    val jail = doubles && doubles && doubles
    println(VariableElimination.probability(jail, true))

    //Exercise 7
    //Here’s a Figaro representation for this game:
    val numberOfSides =
    Select(0.2 -> 4, 0.2 -> 6, 0.2 -> 8, 0.2 -> 12, 0.2 -> 20)
    val roll = Chain(numberOfSides, ((i: Int) => FromRange(1, i + 1)))

    // Exercise 8
    val numberOfSides1 =
      Select(0.2 -> 4, 0.2 -> 6, 0.2 -> 8, 0.2 -> 12, 0.2 -> 20)
    val roll1 = Chain(numberOfSides1, ((i: Int) => FromRange(1, i + 1)))
    val numberOfSides2 =
      Select(0.2 -> 4, 0.2 -> 6, 0.2 -> 8, 0.2 -> 12, 0.2 -> 20)
    val roll2 = Chain(numberOfSides2, ((i: Int) => FromRange(1, i + 1)))

    def stickinessConstraint(sidesPair: (Int, Int)) =
      if (sidesPair._1 == sidesPair._2) 1.0 else 0.5

    val pairOfSides = ^^(numberOfSides1, numberOfSides2)
    pairOfSides.addConstraint(stickinessConstraint)
    println(VariableElimination.probability(roll2, 7))
    // prints 0.05166666666666667
    roll1.observe(7)
    println(VariableElimination.probability(roll2, 7))
    // prints 0.09704301075268815
  }
}