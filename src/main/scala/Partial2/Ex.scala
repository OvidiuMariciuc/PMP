package Partial2

import com.cra.figaro.algorithm.factored.VariableElimination
import com.cra.figaro.language.{Constant, Element, Flip, Select}
import com.cra.figaro.library.compound.{CPD, If, RichCPD}

object Ex {

  class WeatherChange(p1: Double, p2: Double, p3: Double) {
    var state: Element[String] = Select(p1 -> "insorit", p2 -> "ploios", p3 -> "innorat")
  }

  class TakeUmbrela(p: Double) {
    var state: Element[Boolean] = Flip(p)
  }

  def main(args: Array[String]) {

    val length = 10

    val weatherStart = new WeatherChange(0.5, 0.2, 0.3)
    val takeU = new TakeUmbrela(0)

    val hoursW: Array[WeatherChange] =
      Array.fill(length)(weatherStart)

    val takeUmb: Array[TakeUmbrela] =
      Array.fill(length)(takeU)

    for {hour <- 1 until length} {
      hoursW(hour) = new WeatherChange(0.3, 0.3, 0.4)
      val st = CPD(hoursW(hour - 1).state,
        "insorit" -> Select(0.6 -> "insorit", 0.1 -> "ploios", 0.3 -> "innorat"),
        "ploios" -> Select(0.15 -> "insorit", 0.45 -> "ploios", 0.4 -> "innorat"),
        "innorat" -> Select(0.15 -> "insorit", 0.35 -> "ploios", 0.5 -> "innorat")
      )
      hoursW(hour).state = st

    }

    for {hour <- 0 until length} {

      takeUmb(hour) = new TakeUmbrela(0.3)
      val st = CPD(hoursW(hour).state,
        "insorit" -> Flip(0.15),
        "ploios" -> Flip(0.65),
        "innorat" -> Flip(0.75)
      )
      takeUmb(hour).state = st

    }


    for {hour <- 0 until 5} {

      println("O monitorizare pentru o perioadă de 5 ore: " + VariableElimination.probability(takeUmb(hour).state, true))

    }

    hoursW(4).state.observe("innorat")
    hoursW(5).state.observe("innorat")
    println("Iau umbrela?:" + VariableElimination.probability(takeUmb(6).state, true))
    hoursW(4).state.unobserve()
    hoursW(5).state.unobserve()

    takeUmb(5).state.observe(true)
    println("A plouat?:" + VariableElimination.probability(hoursW(2).state, "ploios"))

  }
}


