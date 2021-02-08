package RestMariri

import com.cra.figaro.language.{Constant, Element, Flip, Select}
import com.cra.figaro.library.compound.{CPD, RichCPD}

object ex3 {

  //punctul A - clasa abstracta State
  abstract class State {

  }

  //punctul B - clasa InitialState care este deriv din State
  class InitialState(hidden_state: State) extends State {

  }

  //punctul C clasa NextState care este deriv din State
  class NextState(current_state: State) extends State {
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

    //punctul E - interogarea modelului - aflarea starii ascunse cea mai probabila in fiecare din cele 10 stari

    //punctul F - interogarea modelului - pentru a afla probabilitatea valorii observate în ultima stare,
    //dacă în starea a șasea s-a observat că era “nu prea buna”.

    //punctul G


  }

}
