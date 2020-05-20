package de.htwg.se.malefiz

import de.htwg.se.malefiz.model.{Field, Gameboard, Player, Point}

object Malefiz {
  def main(args: Array[String]): Unit = {
    val student = Player("Your Name")
    println("Hello, " + student.name)

    val gb = Gameboard()
    println(gb.buildField(0))
    println("Robert")
    print("MergeTest")

    var  gamefield = new Array[Field](100)

    for (Int i <- 1 to 132) {
       if (i == 1) {
         gamefield[i] = Field(1, 0, true, false, Point(0,0), false)
         println("                           (-)                           /n")
       }

    }

  }




}
