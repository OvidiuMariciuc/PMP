package Lab8

import com.cra.figaro.algorithm.factored.VariableElimination
import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling._
import com.cra.figaro.library.compound.{*, CPD, OneOf, RichCPD}

/*
Dependintele de la care am plecat :
research and development
human resources
production (research and development , human resources)
sales (production)
finance(human resources, sales)
firm(research and development, human resources, production, sales, finance)
 */

//departamentele research and development si human resources nu depinde de niciun alt departament
class Research_development {
  val state: Element[String] = Select(0.2 -> "poor", 0.5 -> "average", 0.3 -> "productive")
}

class Human_resources {
  val state: Element[String] = Select(0.3 -> "poor", 0.4 -> "average", 0.3 -> "productive")
}

//departamentul production depinde de departamentele research and development si human resources
class Production(research_dev: Research_development, human_res: Human_resources) {
  val state: Element[String] = RichCPD(research_dev.state, human_res.state,
    (OneOf("poor"), *) -> Select(0.6 -> "poor", 0.3 -> "average", 0.1 -> "good"),
    (OneOf("average"), *) -> Select(0.3 -> "poor", 0.6 -> "average", 0.1 -> "good"),
    (OneOf("good"), *) -> Select(0.1 -> "poor", 0.3 -> "average", 0.6 -> "good"),
    (*, OneOf("poor")) -> Select(0.6 -> "poor", 0.3 -> "average", 0.1 -> "good"),
    (*, OneOf("average")) -> Select(0.3 -> "poor", 0.6 -> "average", 0.1 -> "good"),
    (*, OneOf("good")) -> Select(0.1 -> "poor", 0.3 -> "average", 0.6 -> "good"))
}

//departamentul sales depinde de departamentul production
class Sales(production: Production) {
  val state: Element[String] = CPD(production.state,
    "poor" -> Select(0.8 -> "poor", 0.3 -> "average"),
    "average" -> Select(0.2 -> "poor", 0.8 -> "average", 0.3 -> "productive"),
    "productive" -> Select(0.3 -> "average", 0.8 -> "productive"),
  )
}

//departamentul finances depinde de departamentele human resources si sales
class Finances(human_res: Human_resources, sales: Sales) {
  val state: Element[String] = RichCPD(human_res.state, sales.state,
    (OneOf("poor"), *) -> Select(0.6 -> "poor", 0.2 -> "average", 0.2 -> "good"),
    (OneOf("average"), *) -> Select(0.3 -> "poor", 0.6 -> "average", 0.1 -> "good"),
    (OneOf("good"), *) -> Select(0.1 -> "poor", 0.3 -> "average", 0.6 -> "good"),
    (*, OneOf("poor")) -> Select(0.6 -> "poor", 0.2 -> "average", 0.2 -> "good"),
    (*, OneOf("average")) -> Select(0.3 -> "poor", 0.6 -> "average", 0.1 -> "good"),
    (*, OneOf("good")) -> Select(0.1 -> "poor", 0.3 -> "average", 0.6 -> "good"))
}

//firma care depinde de toate cele 5 departamente
class Firm(human_res: Human_resources, sales: Sales, research_dev: Research_development, production: Production, finances: Finances) {
  val state: Element[String] = RichCPD(human_res.state, sales.state, research_dev.state, production.state, finances, state,
    (OneOf("poor"), *, *, *, *) -> Select(0.5 -> "poor", 0.3 -> "average", 0.2 -> "good"),
    (*, OneOf("poor"), *, *, *) -> Select(0.4 -> "poor", 0.3 -> "average", 0.3 -> "good"),
    (*, *, OneOf("poor"), *, *) -> Select(0.6 -> "poor", 0.2 -> "average", 0.2 -> "good"),
    (*, *, *, OneOf("poor"), *) -> Select(0.5 -> "poor", 0.3 -> "average", 0.2 -> "good"),
    (*, *, *, *, OneOf("poor")) -> Select(0.7 -> "poor", 0.2 -> "average", 0.1 -> "good"),
    (OneOf("poor"), *, OneOf("poor"), *, OneOf("poor")) -> Select(0.8 -> "poor", 0.1 -> "average", 0.1 -> "good"),
    (OneOf("average"), *, OneOf("average"), *, OneOf("average")) -> Select(0.1 -> "poor", 0.8 -> "average", 0.1 -> "good"),
    (OneOf("good"), *, OneOf("good"), *, OneOf("good")) -> Select(0.1 -> "poor", 0.1 -> "average", 0.8 -> "good"))
}

object Ex1 {
  def main(args: Array[String]) {
    /*val test = Constant("Test")

    val algorithm = Importance(1000, test)
    algorithm.start()

    println(algorithm.probability(test, "Test"))*/
  }

  //crearea fiecarui departament
  val research_dev = new Research_development
  val human_res = new Human_resources
  val production = new Production(research_dev, human_res)
  val sales = new Sales(production)
  val finances = new Finances(human_res, sales)
  val firm = new Firm(human_res, sales, research_dev, production, finances)

  //cateva teste/exemple(afisari)
  print(VariableElimination.probability(production.state, "good"))
  print(VariableElimination.probability(research_dev.state, "average"))
  print(VariableElimination.probability(finances.state, "poor"))
  print(VariableElimination.probability(firm.state, "poor"))
}