import com.cra.figaro.algorithm.factored.VariableElimination
import com.cra.figaro.language.{Flip, Universe}
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