package de.htwg.se.malefiz.aview

import de.htwg.se.malefiz.model.Player

import scala.io.StdIn

class Tui {


  var colors = Array("Red", "Green", "Yellow", "Blue")

  def processInput(input: String)  : String = {
    input match {
      case "new Game" =>
        println ("Wieviele Spieler?")
        val a = io.StdIn.readInt()
        for (i <- 1 to a if i < 4) {
          println("Spieler " + i +". Bitte geben Sie ihren Namen ein")
          val name = io.StdIn.readLine()
          println("Spieler " + i +". Bitte wählen Sie eine Farbe aus (Red, Green, Yellow or Blue")
          val color = io.StdIn.readLine()

          Player(name, color, i)
        }
        ""
    }
  }
}
