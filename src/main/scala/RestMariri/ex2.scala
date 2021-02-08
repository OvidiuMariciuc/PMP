package RestMariri

import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.experimental.normalproposals.Normal
import com.cra.figaro.language.Select

object ex2 {

  def main(args: Array[String]) {

    //exercitiul 2
    //punctul A
    //media = 7 si varianta = 5
    val avg_temperature = Normal(7, 5)

    //varianta pentru temperature
    val var_temperature = Select(0.5 -> 20, 0.5 -> 30)

    //media = avg_temperature si varianta = var_temperature
    val temperature = Normal(avg_temperature, var_temperature)

    //punctul B
    //verificam daca temp se afla intre 20 si 50 de grade
    def greaterBetween20and50(d: Double) = d > 50 && d < 20

    //folosim importance deoarece lucram cu valori continue (nu discrete)
    println(Importance.probability(temperature, greaterBetween20and50 _))

    //marcam ca temperatura medie este 9
    avg_temperature.observe(9)
    println(Importance.probability(temperature, greaterBetween20and50 _))
    //demarcam observarea pentru a nu influenta celalalte interogari
    avg_temperature.unobserve()


  }

}
