package de.htwg.se.malefiz

import de.htwg.se.malefiz.model.{Gameboard, GameboardString, GenFields, Player}

object Malefiz {
  def main(args: Array[String]): Unit = {

    println("Willkommen bei Malefiz!");
    println("Wie viele Spieler möchten Spielen? (2-4 Spieler)")

    val student = Player("Your Name")
    println("Hello, " + student.name)

    val gbs = GameboardString()
    println(gbs.buildField(4))

    val gf = GenFields()
    val t = gf.genCells(112)
    /*for (i <- t)
      println(i)*/

    val gb = Gameboard()
    val graph = gb.generateOriginal(t)
    for((k,v) <- graph) {
      println(k + " ---> " + v)
    }
println(graph.size)
  }
}
