package Lab9

import com.cra.figaro.algorithm.factored.VariableElimination
import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling._
import com.cra.figaro.library.atomic.discrete.FromRange
import com.cra.figaro.library.compound.{If, OneOf, RichCPD}

object Ex1 {
  def main(args: Array[String]) {
    //Exercitiul 1

    //vectorul cu cele 10 capitole pe care trebuie sa le invete
    val length = 10

    val sChapters: Array[Element[Boolean]] =
      Array.fill(length)(Constant(false))

    //vectorul cu cele 10 teste pe care le va da studentul din cele 10 capitole
    val sTests: Array[Element[Boolean]] =
      Array.fill(length)(Constant(false))

    //sansa de a invata pentru primul capitol
    sChapters(0) = Flip(0.8)
    //sansa de a trece primul test care depinde daca a invatat la primul capitol sau nu
    sTests(0) = If(sChapters(0), Flip(0.8), Flip(0.2))

    //sansa de a invata pentru urmatoarele capitole (care depinde de capitolul anterior, daca a invatat sau nu)
    for {chapter <- 1 until length} {
      sChapters(chapter) = If(sChapters(chapter - 1), Flip(0.6), Flip(0.3))
    }

    //sansa de a trece la urmatoarele teste (care depinde daca a invatat la capitolul respectiv sau nu)
    for {test <- 1 until length} {
      sTests(test) = If(sChapters(test), Flip(0.8), Flip(0.2))
    }

    //studentul a trecut primele 3 teste
    sTests(0).observe(true)
    sTests(1).observe(true)
    sTests(2).observe(true)
    //verificam daca studentul a trecut ultimul test stiind ca le-a trecut pe primele 3
    println(VariableElimination.probability(sTests(9), true))


    //Exercitiul 2
    //vectorul cu notele pentru fiecare test
    val sGrades: Array[Element[Int]] =
    Array.fill(length)(Constant(0))

    //vectorul cu cele 10 rezultate pentru fiecare test(daca a trecut sau nu, in functie de scor))
    val tPassed: Array[Element[Boolean]] =
      Array.fill(length)(Constant(false))

    //nota la primul test care depinde daca a invatat la primul capitol sau nu
    sGrades(0) = If(sChapters(0), FromRange(7, 10), FromRange(2, 5))
    //consideram ca un student a trecut un test daca a are nota la test >=5
    //rezultatul final pentru primul test care depinde de scorul obtinut
    tPassed(0) = RichCPD(sGrades(0),
      OneOf(1, 2, 3, 4) -> Flip(0),
      OneOf(5, 6, 7, 8, 9, 10) -> Flip(1)
    )

    //nota la urmatoarele teste (care depinde daca a invatat la capitolul respectiv sau nu)
    for {grade <- 1 until length} {
      sGrades(grade) = If(sChapters(grade), FromRange(6, 10), FromRange(1, 5))
    }

    //consideram ca un student a trecut un test daca a are nota la test >=5
    //rezultatul final la urmatoarele teste (care depinde de scorul obtinut la test)
    for {score <- 1 until length} {
      tPassed(score) = RichCPD(sGrades(score),
        OneOf(1, 2, 3, 4) -> Flip(0),
        OneOf(5, 6, 7, 8, 9, 10) -> Flip(1)
      )
    }

    //studentul a trecut primele 3 teste, adica a avut note >=5 la toate cele 3 testele
    tPassed(0).observe(true)
    tPassed(1).observe(true)
    tPassed(2).observe(true)
    //verificam daca studentul a trecut ultimul test stiind ca le-a trecut pe primele 3
    println(VariableElimination.probability(tPassed(9), true))

    //rezultate afisare :
    //ex1 :0.4571958838353301
    //ex2 :0.4286601449197861
  }
}