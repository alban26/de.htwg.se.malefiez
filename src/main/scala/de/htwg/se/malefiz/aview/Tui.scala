package de.htwg.se.malefiz.aview

import de.htwg.se.malefiz.model.{Cube, GenFields, Player}

import scala.io

class Tui {

  var colors = Array("Red", "Green", "Yellow", "Blue")

  def processInput(input: String)  : String = {
    input match {
      case "new Game" =>
        println ("Wieviele Spieler?")
        val a = io.StdIn.readInt()
        val b = GenFields()
        val c = b.genCells(4)
        println(c)
        for (i <- 1 to a if i < 4) {
          println("Spieler " + i +". Bitte geben Sie ihren Namen ein")
          val name = io.StdIn.readLine()
          println("Spieler " + i +". Bitte wählen Sie eine Farbe aus (Red, Green, Yellow or Blue")
          val color = io.StdIn.readLine()

        }
        ""
      case "cube" =>
        val randomNumber = Cube()
        println(randomNumber.getRandomNumber)

        ""
    }
  }
}
