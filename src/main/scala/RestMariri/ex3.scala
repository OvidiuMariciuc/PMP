package RestMariri

import Partial2.Ex.WeatherChange
import com.cra.figaro.algorithm.factored.VariableElimination
import com.cra.figaro.language.{Constant, Element, Flip, Select}
import com.cra.figaro.library.compound.{CPD, RichCPD}

object ex3 {

  //punctul A - clasa abstracta State
  abstract class State {

  }

  //punctul B - clasa InitialState care este deriv din State
  class InitialState(hidden_state: String) extends State {
    val initial_state = Select(0.4 -> "buna", 0.3 -> "nu prea buna", 0.2 -> "bolnav", 0.1 -> "decedat")
  }

  //punctul C clasa NextState care este deriv din State
  class NextState(current_state: String) extends State {
    var state: Element[String] = CPD(current_state,
      "A" -> Select(0.721 -> "A", 0.202 -> "B", 0.067 -> "C", 0.1 -> "D"),
      "B" -> Select(0.581 -> "B", 0.407 -> "C", 0.012 -> "D"),
      "C" -> Select(0.75 -> "C", 0.25 -> "D"),
      "D" -> Select(1.0 -> "D")
    )

  }

  def main(args: Array[String]) {

    //exercitiul 3
    ///model Markov - starile ascunse A,B,C,D
    ///valorile observabile - buna, nu prea buna, bolnav, decedat

    val length = 10

    ///modelarea tabelului
    val A = Select(0.4 -> "buna", 0.3 -> "nu prea buna", 0.2 -> "bolnav", 0.1 -> "decedat")
    val B = Select(0.4 -> "buna", 0.3 -> "nu prea buna", 0.2 -> "bolnav", 0.1 -> "decedat")
    val C = Select(0.4 -> "buna", 0.3 -> "nu prea buna", 0.2 -> "bolnav", 0.1 -> "decedat")
    val D = Select(0.4 -> "buna", 0.3 -> "nu prea buna", 0.2 -> "bolnav", 0.1 -> "decedat")

    //punctul D - creearea listei cu cele 10 stari - stiind ca starea initiala este A

    //starea initiala este A
    val initial_state = new InitialState("A")

    //lista cu cele 10 stari posibile
    val states: Array[State] =
      Array.fill(length)(initial_state)

    //lista cu cele 10 stari posibile + valorile observate in acestea
    val states_val: Array[State] =
      Array.fill(length)(initial_state)

    //punctul E - interogarea modelului - aflarea starii ascunse cea mai probabila in fiecare din cele 10 stari

    for {s <- 0 until 10} {
      //println(VariableElimination.probability(states(s).state))
    }

    //punctul F - interogarea modelului - pentru a afla probabilitatea valorii observate în ultima stare,
    //dacă în starea a șasea s-a observat că era “nu prea buna”.

    states_val(6).observe("nu prea buna")
    //println(VariableElimination.probability(states_val(9))
    states_val(6).unobserve

    //punctul G

    states_val(6).observe("bolnav")
    for {s <- 0 until 10} {
      //println(VariableElimination.probability(states(s).state))
    }
    states_val(6).unobserve


  }

}
