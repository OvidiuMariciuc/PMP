package Lab11

import com.cra.figaro.algorithm.factored.VariableElimination
import com.cra.figaro.algorithm.factored.beliefpropagation.BeliefPropagation
import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling._
import com.cra.figaro.library.compound.If

object Ex1 {
  def main(args: Array[String]) {
    //LABORATOR 11 - EX 1
    //punctul a
    /*val lfthandedPresident = Flip(0.5)
    val lfthandedSomeone = Flip(0.1)*/

    //1 din 40 de mil poate sa devina presedinte
    val becomePresident = Flip(0.000000025)
    //50% din presedinti sunt stangaci si 10% din pop. generala sunt stangaci
    val left_handed = If(becomePresident, Flip(0.5), Flip(0.1))

    left_handed.observe(true)
    println("\nPunctul a: Probabilitatea ca va deveni presedinte folosind VE:" + VariableElimination.probability(becomePresident, true))
    println("Punctul a: Probabilitatea ca va deveni presedinte folosind Importance:" + Importance.probability(becomePresident, true))
    println("Punctul a: Probabilitatea ca va deveni presedinte folosind BP:" + +BeliefPropagation.probability(becomePresident, true))
    left_handed.unobserve()

    //punctul b
    //15% din presedinti merg la Harvard si 1 din 2000 din pop. generala
    val wentHarvard = If(becomePresident, Flip(0.15), Flip(0.0005))

    wentHarvard.observe(true)
    println("\nPunctul b: Probabilitatea ca va deveni presedinte folosind VE:" + VariableElimination.probability(becomePresident, true))
    println("Punctul b: Probabilitatea ca va deveni presedinte folosind Importance:" + Importance.probability(becomePresident, true))
    println("Punctul b: Probabilitatea ca va deveni presedinte folosind BP:" + +BeliefPropagation.probability(becomePresident, true))
    wentHarvard.unobserve()

    //punctul c
    left_handed.observe(true)
    wentHarvard.observe(true)
    println("\nPunctul c: Probabilitatea ca va deveni presedinte folosind VE:" + VariableElimination.probability(becomePresident, true))
    println("Punctul c: Probabilitatea ca va deveni presedinte folosind Importance:" + Importance.probability(becomePresident, true))
    println("Punctul c: Probabilitatea ca va deveni presedinte folosind BP:" + +BeliefPropagation.probability(becomePresident, true))
    left_handed.unobserve()
    wentHarvard.unobserve()

    /*Comparand cu rezultatele din [SLE] acestea sunt diferite, deoarece apar diferente la definirea variabilelor cum ar fi
    P(LH/POTUS) care in problema este 1/2 dar in solutii este 1/3.
    La subpunctul a la numarator, spre deosebire de subpunctele b si c, inlocuirea numerica a fost facuta gresita, 40 de mil.
    in loc de 1/40 de mil.
    la b si c rezultatele sunt asemanatoare cu cele din [SLE]
    Rezultate:
    Punctul a: Probabilitatea ca va deveni presedinte folosind VE:1.2499998750000125E-7
    Punctul a: Probabilitatea ca va deveni presedinte folosind Importance:0.0
    Punctul a: Probabilitatea ca va deveni presedinte folosind BP:1.2499998750000117E-7

    Punctul b: Probabilitatea ca va deveni presedinte folosind VE:7.4999439379190625E-6
    Punctul b: Probabilitatea ca va deveni presedinte folosind Importance:0.0
    Punctul b: Probabilitatea ca va deveni presedinte folosind BP:7.4999439379190346E-6

    Punctul c: Probabilitatea ca va deveni presedinte folosind VE:3.74985947401621E-5
    Punctul c: Probabilitatea ca va deveni presedinte folosind Importance:0.0
    Punctul c: Probabilitatea ca va deveni presedinte folosind BP:3.74985947401621E-5
    */

  }
}