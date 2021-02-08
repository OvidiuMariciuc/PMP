package RestMariri

import com.cra.figaro.language.{Constant, Element}

object ex3 {

  class State {

  }

  def main(args: Array[String]) {

    //exercitiul 3
    ///model Markov - starile ascunse A,B,C,D
    ///valorile observabile - buna, nu prea buna, bolnav, decedat

    val length = 10

    val A: Array[Element[Double]] = Array.fill(length)(Constant(0.0))
    val B: Array[Element[Double]] = Array.fill(length)(Constant(0.0))
    val C: Array[Element[Double]] = Array.fill(length)(Constant(0.0))
    val D: Array[Element[Double]] = Array.fill(length)(Constant(0.0))


  }

}
