package Lab3

import com.cra.figaro.algorithm.factored.VariableElimination
import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling._
import com.cra.figaro.library.compound.CPD

object Covid {
	Universe.createNew()
	private val febra = Flip(0.06)
	private val tuse = Flip(0.04)
	private val covid = CPD(febra, tuse,
		(false, false) -> Flip(0.0001),
		(false, true) -> Flip(0.1),
		(true, false) -> Flip(0.5),
		(true, true) -> Flip(0.99))

	def main(args: Array[String]): Unit = {
		//covid.observe(true)
		febra.observe(true);
		tuse.observe(true);
		val alg = VariableElimination(febra, tuse,covid)
		alg.start()
		/*
    println("Probability of febra: " + alg.probability(febra,
      true))
		println("Probability of tuse: " + alg.probability(tuse,
			true))
			*/
		println("Probability of covid: " + alg.probability(covid,
			true))
		alg.kill
	}

}

/*
object Burglary {
  Universe.createNew()
  private val burglary = Flip(0.01)
  private val earthquake = Flip(0.0001)
  private val alarm = CPD(burglary, earthquake,
    (false, false) -> Flip(0.001),
    (false, true) -> Flip(0.1),
    (true, false) -> Flip(0.9),
    (true, true) -> Flip(0.99))
  private val johnCalls = CPD(alarm,
    false -> Flip(0.01),
    true -> Flip(0.7))

  def main(args: Array[String]): Unit = {
    johnCalls.observe(true)
    val alg = VariableElimination(burglary, earthquake)
    alg.start()
    println("Probability of burglary: " + alg.probability(burglary,
      true))
    alg.kill
  }
}
*/


/*object Ex1 {
  def main(args: Array[String]) {
    val test = Constant("Test")

    val algorithm = Importance(1000, test)
    algorithm.start()

    println(algorithm.probability(test, "Test"))
    val helloWorldElement = Constant("Hello world!")
    val sampleHelloWorld = Importance(1000, helloWorldElement)
    sampleHelloWorld.start()
    println("Probability of Hello world:")
    println(sampleHelloWorld.probability(helloWorldElement, "Hello world!"))
    println("Probability of Goodbye world:")
    println(sampleHelloWorld.probability(helloWorldElement, "Goodbye world!"))

  }
}*/
