package RestMariri

import com.cra.figaro.algorithm.factored.VariableElimination
import com.cra.figaro.language.{Constant, Flip}
import com.cra.figaro.library.atomic.discrete.Binomial
import com.cra.figaro.library.compound.{CPD, OneOf, RichCPD}

object ex1 {

  def main(args: Array[String]) {

    //exercitiul 1
    //val s = Flip(0.6)
    //punctul A

    //saptamana - 7 zile, sansa sa ninga intr-o zi = 0.6
    //rezultatul lui week va fi numarul de zile in care va ninge
    val week = Binomial(7, 0.6)

    //calitatea saptamanii care este data in functie de val week adica in functie de numarul de zile in care a nins
    val week_quality = RichCPD(week,
      (OneOf(6, 7)) -> Constant("prea multa ninsoare"),
      (OneOf(1, 2)) -> Constant("prea multa ninsoare"),
      (OneOf(3, 4, 5)) -> Constant("prea multa ninsoare")
    )

    //punctul B
    println(VariableElimination.probability(week_quality, "normala"))

    //punctul C

    val length = 10 //10 saptamani
    //vom genera 10 calitati pentru 10 saptamani diferite
    //cu week_qual parcurgem fiecare saptamana pe rand
    for {week_qual <- 1 until length} {
      //println("Calitatea saptamanii:" + week_quality(week_qual))
    }

    //observam ca o saptamana normala apare mai des decat celelalte 2 tipuri de saptamani
    //normala va aparea mai des deoarece este probabilitatea mai mare sa fie o saptamana normala(3 zile cu ninsoare)
    //decat sa fie o saptamana cu prea multa sau prea putina ninsoare(2 zile cu ninsoare pentru fiecare)


  }

}
