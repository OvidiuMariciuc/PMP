package Partial2

import com.cra.figaro.algorithm.factored.VariableElimination
import com.cra.figaro.language.{Constant, Element, Flip, Select}
import com.cra.figaro.library.compound.{CPD, OneOf, RichCPD}

object Ex {

  //Partial 2 - Model Markov askuns
  // descrierea modelului
  //clasa Weather
  class Weather(state_weather: Element[String]) {
    val state: Element[String] = state_weather
  }

  //clasa takeUmbrela
  class takeUmbrela(umbrela: Element[Boolean]) {
    val take_umbrela: Element[Boolean] = umbrela
  }

  //functia main a programului principal
  def main(args: Array[String]): Unit = {
    //lungimea vectorilor
    val length = 20

    //variabila pentru vreme
    //starea initiala a vremii (Start)
    val weather = Select(0.5 -> "insorit", 0.2 -> "ploios", 0.3 -> "innorat")
    val weatherCatalog: Array[Weather] = Array.fill(length)(new Weather(weather))
    //initializarea vremii - starea 0
    weatherCatalog(0) = new Weather(weather)


    //variabila pentru take_umbrela
    //initial sansa sa luam umbrela este de 0%
    val take_umbrela = Flip(0)
    val umbrela: Array[takeUmbrela] = Array.fill(length)(new takeUmbrela(take_umbrela))

    //schimbarea vremii in functie de vremea anterioara (folosind stiute prob. din enunt)
    //parcurgem fiecare ora si actualizam vremea in functie de vremea anterioara
    for {hour <- 1 until length} {
      val vreme = CPD(weather(hour - 1).state,
        "insorit" -> Select(0.6 -> "insorit", 0.1 -> "ploios", 0.3 -> "innorat"),
        "ploios" -> Select(0.15 -> "insorit", 0.45 -> "ploios", 0.4 -> "innorat"),
        "innorat" -> Select(0.15 -> "insorit", 0.35 -> "ploios", 0.5 -> "innorat")
      )
      //actualizam noua vreme
      weather(hour) = new Weather(vreme)
    }

    //luarea deciziei de a lua umbrela in functie de vremea curenta(folosind stiute prob. din enunt)
    //parcurgem fiecare ora si actualizam decizia daca sa luam umbrela sau nu
    for {hour <- 0 until length} {
      val take_umbrela = CPD(weather(hour).state,
        "insorit" -> Flip(0.15),
        "ploios" -> Flip(0.75),
        "innorat" -> Flip(0.65)
      )
      //actualizam noua decizie
      take_umbrela(hour) = new takeUmbrela(take_umbrela)

    }

    //monitorizarea vremii + afisari + rezultate (interogari)

    //subpct a
    //monitorizam vremea pe o perioada de 5 ore afisand ora urmata de actiunea de a lua umbrela sau nu
    for {hour <- 0 until 5} {
      println("Probabilitate ca voi lua umbrela la ora-" + hour + "este -> "
        + VariableElimination.probability(umbrela(hour).take_umbrela, true))
    }

    //subpct b
    //observam ca la orele 4 si 5 vremea a fost innorata
    weatherCatalog(4).state.observe("innorat")
    weatherCatalog(5).state.observe("innorat")
    //afisam rezultatul vremii pentru de la ora 6
    println("Probabilitate sa iau umbrela la ora 6 -> "
      + VariableElimination.probability(umbrela(6).take_umbrela, true))

    //subpct c
    //observam ca la ora 5 umbrela nu a fost luata
    umbrela(5).take_umbrela.observe(false)
    //afisam probabilitatea ca acum 3 ore sa fi plouat adica ca vreme = "ploios"
    println("Probabilitate sa fi plouat acum 3 ore ->"
      + VariableElimination.probability(weatherCatalog(3).state, "ploios"))


  }
}