package partial

import com.cra.figaro.algorithm.factored.VariableElimination
import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling._
import com.cra.figaro.experimental.normalproposals.Beta
import com.cra.figaro.library.compound.{*, CPD, OneOf, RichCPD}
import com.cra.figaro.language.{Apply, Constant, Flip}

object partial {
  def main(args: Array[String]) {

    //val set_alarma = Flip(0.9) //90% din timp iti setezi alarma
    val autobuz = Flip(0.8) //80% din cazuri autobuzul nu intarzie
    val intarziat = Flip(0.1) //daca s-a trezit tarziu sau nu
    //aveam aici si varianta cu cele 2 val-uri suplimentare : adormi_la_loc si intarziat care depindeau de alarma
    //adormi_la_loc depinde de set_alarma (daca este setata)
    //val adormi_la_loc = RichCPD(set_alarma, (OneOf(true)) -> Flip(0.1))
    //intarziat depinde de set_alarma (daca este setata)
    //val intarziat = RichCPD(set_alarma, (OneOf(false)) -> Flip(0.9))


    //ajungi_la_timp depinde si de val intarziat si de autobuz (daca acesta a intarziat sau nu)
    //fiecare caz - traducerea fiecarei linii din tabel
    val ajungi_la_timp = CPD(intarziat, autobuz,
      (true, true) -> Flip(0.1),
      (true, false) -> Flip(0.3),
      (false, true) -> Flip(0.2),
      (false, false) -> Flip(0.9)
    )

    //setam intarziat_pe true pentru ca a adormit la loc
    intarziat.observe(true)
    //interogarea de la punctul a
    println(VariableElimination.probability(ajungi_la_timp, true))
    //unsetam valoarea pusa anterior pentru a nu afecta restul interogarilor
    intarziat.unobserve()

    //setam ajungi_la_timp pe true pentru ca a ajuns la serviciu
    ajungi_la_timp.observe(true)
    //interogarea de la punctul b
    println(VariableElimination.probability(intarziat, false))
    //unsetam valoarea pusa anterior pentru a nu afecta restul interogarilor
    ajungi_la_timp.unobserve()

    //interogarea de la punctul c
    println(VariableElimination.probability(intarziat, true))

    //pentru punctul 3
    val bias = Beta(2,5)
    val intarziat2 = Flip(bias)
  }
}