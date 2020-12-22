package Lab10

import com.cra.figaro.algorithm.factored.VariableElimination
import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling._
import com.cra.figaro.library.compound.^^

object Ex1Varianta2 {
  def main(args: Array[String]) {

    val length = 11
    val investmentV: Array[Element[Int]] = Array.fill(length)(Constant(0))
    val profitV: Array[Element[Int]] = Array.fill(length)(Constant(0))
    //At any time step, capital is equal to the
    //previous capital plus new profit minus new investment.
    val capitalV: Array[Element[Int]] = Array.fill(length)(Constant(0))

    //valoarea initiala pentru capital
    capitalV(0) = Constant(1000)

    //investia reprezinta 20% din capital
    //profitul care se schimba in functia de valoarea investitiilor
    def transition(capital: Element[Int]): (Element[(Int, Int, Int)]) = {
      val newInvestment: Element[Int] = Apply(capital, (i: Int) => i * (1 / 5))
      val newProfit: Element[Int] = Apply(capital, (i: Int) => i + i * (1 / 6))
      val newCapital: Element[Int] = Apply(capital, newInvestment, newProfit, (i: Int, new_inv: Int, new_prof: Int) => i + i + new_prof - new_inv)
      ^^(newInvestment, newProfit, newCapital)
    }

    //verificam daca este mai mare decat valoarea initiala
    println("predict the amount of capital after 10 time steps: " +
      VariableElimination.probability(capitalV(length - 1), (i: Int) => i > 1000))

    //verificam daca si-a dublat valoarea
    println("predict the amount of capital after 10 time steps: " +
      VariableElimination.probability(capitalV(length - 1), (i: Int) => i > 2000))
  }
}