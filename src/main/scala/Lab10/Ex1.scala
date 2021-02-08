package Lab10

import com.cra.figaro.algorithm.factored.VariableElimination
import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling._

object Ex1 {
  def main(args: Array[String]) {

    val length = 11
    val investmentV: Array[Element[Int]] = Array.fill(length)(Constant(0))
    val profitV: Array[Element[Int]] = Array.fill(length)(Constant(0))
    //At any time step, capital is equal to the
    //previous capital plus new profit minus new investment.
    val capitalV: Array[Element[Int]] = Array.fill(length)(Constant(0))

    //valoarea initiala pentru capital
    capitalV(0) = Constant(1000)
    //investia reprezinta 33% din capital
    for {capital <- 0 until length} {
      investmentV(capital) = Apply(capitalV(capital), (i: Int) => i * (1 / 3))
    }

    //profitul care se schimba in functia de valoarea investitiilor
    for {investment <- 0 until length} {
      profitV(investment) = Apply(investmentV(investment), (i: Int) => i + i * (1 / 6))

    }

    for {step <- 1 until length} {
      capitalV(step) = Apply(capitalV(step - 1), investmentV(step), profitV(step), (i: Int, inv: Int, prof: Int) => i + prof - inv)

    }

    //verificam daca este mai mare decat valoarea initiala
    println("predict the amount of capital after 10 time steps: " +
      VariableElimination.probability(capitalV(length - 1), (i: Int) => i > 1000))

    //verificam daca si-a dublat valoarea
    println("predict the amount of capital after 10 time steps: " +
      VariableElimination.probability(capitalV(length - 1), (i: Int) => i > 2000))
  }
}