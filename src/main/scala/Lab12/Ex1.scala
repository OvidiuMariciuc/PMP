package Lab12

import com.cra.figaro.algorithm.factored.VariableElimination
import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling._
import com.cra.figaro.experimental.normalproposals.Normal
import com.cra.figaro.library.compound.If
import scalax.chart.api._

object Ex1 {
  def main(args: Array[String]) {
    /*val test = Constant("Test")

    val algorithm = Importance(1000, test)
    algorithm.start()

    println(algorithm.probability(test, "Test"))*/

    val x0 = Apply(Normal(0.75, 0.3), (d: Double) => d.max(0).min(1))

    val y0 = Apply(Normal(0.4, 0.2), (d: Double) => d.max(0).min(1))

    val x = Flip(x0)

    val y = Flip(y0)

    val z = If(x === y, Flip(0.8), Flip(0.2))

    //exercitiul 1
    val veAnswer1 = VariableElimination.probability(y, true)
    var data_plot1: List[(Int, Double)] = List()
    for {i <- 1000 to 10000 by 1000} {
      var totalSquaredError = 0.0
      for {j <- 1 to 100} {
        val imp = Importance(i, y)
        imp.start()
        val impAnswer = imp.probability(y, true)
        val diff = veAnswer1 - impAnswer
        totalSquaredError += diff * diff
      }
      val rmse = math.sqrt(totalSquaredError / 100)
      data_plot1 = data_plot1 :+ (i, rmse)
      println(i + " samples: RMSE = " + rmse)
    }
    val plot1 = XYLineChart(data_plot1, title = "Importance-sampling")
    plot1.show()

    //exercitiul 2
    val veAnswer2 = VariableElimination.probability(y, true)
    var data_plot2: List[(Int, Double)] = List()
    for {i <- 10000 to 100000 by 10000} {
      var totalSquaredError = 0.0
      for {j <- 1 to 100} {
        Universe.createNew()
        z.observe(false)
        val mh = MetropolisHastings(i, ProposalScheme.default, y)
        mh.start()
        val mhAnswer = mh.probability(y, true)
        val diff = veAnswer2 - mhAnswer
        totalSquaredError += diff * diff
      }
      val rmse = math.sqrt(totalSquaredError / 100)
      data_plot2 = data_plot2 :+ (i, rmse)
      println(i + " samples: RMSE = " + rmse)
    }
    val plot2 = XYLineChart(data_plot1, title = "Importance-sampling")
    plot2.show()

    //exercitiul 3
    val veAnswer3 = VariableElimination.probability(y, true)
    var data_plot3: List[(Int, Double)] = List()
    for {i <- 10000 to 1000000 by 10000} {
      var totalSquaredError = 0.0
      for {j <- 1 to 100} {
        Universe.createNew()
        z.observe(false)
        val mh = MetropolisHastings(i, ProposalScheme.default, y)
        mh.start()
        val mhAnswer = mh.probability(y, true)
        val diff = veAnswer3 - mhAnswer
        totalSquaredError += diff * diff
      }
      val rmse = math.sqrt(totalSquaredError / 100)
      data_plot3 = data_plot3 :+ (i, rmse)
      println(i + " samples: RMSE = " + rmse)
    }
    val plot3 = XYLineChart(data_plot1, title = "Importance-sampling")
    plot3.show()

    //exercitiul 4
    val veAnswer4 = VariableElimination.probability(y, true)
    var data_plot4: List[(Int, Double)] = List()
    for {i <- 10000 to 10000000 by 10000} {
      var totalSquaredError = 0.0
      for {j <- 1 to 100} {
        Universe.createNew()
        z.observe(false)
        val mh = MetropolisHastings(i, ProposalScheme.default, y)
        mh.start()
        val mhAnswer = mh.probability(y, true)
        val diff = veAnswer4 - mhAnswer
        totalSquaredError += diff * diff
      }
      val rmse = math.sqrt(totalSquaredError / 100)
      data_plot4 = data_plot4 :+ (i, rmse)
      println(i + " samples: RMSE = " + rmse)
    }
    val plot4 = XYLineChart(data_plot1, title = "Importance-sampling")
    plot4.show()

    //exercitiul 5
    val z1 = Flip(0.8)
    val z2 = Flip(0.2)
    val scheme = DisjointScheme(
      0.1 -> (() => ProposalScheme(z1)),
      0.1 -> (() => ProposalScheme(z2)),
      0.8 -> (() => ProposalScheme(x, y))
    )
    val veAnswer5 = VariableElimination.probability(y, true)
    var data_plot5: List[(Int, Double)] = List()
    for {i <- 10000 to 10000000 by 10000} {
      var totalSquaredError = 0.0
      for {j <- 1 to 100} {
        Universe.createNew()
        z.observe(false)
        val mh = MetropolisHastings(i, ProposalScheme.default, y)
        mh.start()
        val mhAnswer = mh.probability(y, true)
        val diff = veAnswer5 - mhAnswer
        totalSquaredError += diff * diff
      }
      val rmse = math.sqrt(totalSquaredError / 100)
      data_plot5 = data_plot5 :+ (i, rmse)
      println(i + " samples: RMSE = " + rmse)
    }
    val plot5 = XYLineChart(data_plot1, title = "Importance-sampling")
    plot5.show()


  }
}