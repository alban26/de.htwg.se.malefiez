package de.htwg.se.malefiz

import de.htwg.se.malefiz.model.{Gameboard, GameboardString, GenFields, Player}

object Malefiz {
  def main(args: Array[String]): Unit = {
    val student = Player("Your Name")
    println("Hello, " + student.name)

    val gb = GameboardString()
    println(gb.buildField(4))

    val gf = GenFields()
    gf.genCells(112)
  }
}
