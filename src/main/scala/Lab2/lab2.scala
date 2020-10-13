object lab2 {

  def main(args: Array[String]): Unit = {
    //print("HelloWorld")
    def infoPersoana(person: Person): Unit = {
      person match {
        case Student(nume, prenume, materii) =>
          print(s"Studentul $nume $prenume urmeaza materiile: $materii")
        case Profesor(nume, prenume, materie) =>
          print(s"Profesorul $nume $prenume preda materiile: $materie")
      }

    }
    val profesor = new Profesor("Ionescu", "Vasile", "IA")
    //val student = new Student(nume="Mariciuc",prenume = "Ovidiu", materii = "PMP" , 10 )
    infoPersoana(profesor)
  }
}

abstract class Person

case class Student(var nume: String, var prenume: String, var materii: Array[(String, Integer)]) extends Person {
  //materii = materii :+ ("PMP", 10)
  def setNota(materie: String, nota: Integer) = {
    for (i <- 0 until materii.length) {
      if (materii(i)._1 == materie) {
        materii(i) = (materie, nota)
      }
    }
  }

  def getNota(materie: String): Unit = {
    for (i <- 0 until materie.length) {
      if (materii(i)._1 == materie) {
        materii(i)._2
      }
    }
  }

  def addMaterie(materie: String, nota: Integer): Unit = {
    materii = materii :+ (materie, nota)
  }

}

  case class Profesor(var nume: String, var prenume: String, var materie: String) extends Person {
  }