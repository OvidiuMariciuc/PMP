package Lab7

import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling._
import com.cra.figaro.experimental.normalproposals.Uniform
import com.cra.figaro.library.atomic.discrete.FromRange

object Ex1 {
	def main(args: Array[String]) {
		//var par = List( ...) - ///lista cu paruri pt fiecare gaura
		val par = List(3,5,4,3,3,4,4,5,3,5,4,3,5,5,3,4,5,3)
		val skill = Uniform(0.0, 8.0 / 13.0)
		//var shots = List.tabulate(18)((holeIndex:Int) => Chain(...))
		val shots = Array.tabulate(18)(i => Chain(skill,
			(s: Double) => Select(s / 8.0 -> (par(i) - 2),
				s / 2.0 -> (par(i) - 1),
				s -> par(i),
				4 / 5 * (1 - 13 * s / 8) -> (par(i) + 1),
				1 / 5 * (1 - 13 * s / 8) -> (par(i) + 2)
			)))
		///sau
		///var shots = for {holeIndex <- 0 until 18) yield Chain(...)

		/*def greaterThan80(s: Int) = s >= 80
		alg.probability(sum, greaterThan80 _)*/

										 }
}