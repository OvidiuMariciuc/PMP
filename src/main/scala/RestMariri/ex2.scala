package RestMariri

import com.cra.figaro.experimental.normalproposals.Normal
import com.cra.figaro.language.Select

object ex2 {

  def main(args: Array[String]) {

    //exercitiul 2
    //punctul A
    //distr normala = 7 si varianta = 5
    val avg_temperature = Normal(7,5)

    val var_temperature = Select(0.5 -> 20, 0.5 -> 30)

  }

}
