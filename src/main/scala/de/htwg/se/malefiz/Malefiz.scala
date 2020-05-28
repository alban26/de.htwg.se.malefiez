package de.htwg.se.malefiz

import de.htwg.se.malefiz.aview.Tui
import de.htwg.se.malefiz.model.{Cube, Gameboard, GenFields, Player}

import scala.io.StdIn

object Malefiz {

  var playerList = new Array[Player] (4)
  val tui = new Tui
  var test = ""
  def main(args: Array[String]): Unit = {

    println("Willkommen bei Malefiz!")

    var input: String = ""

    do {
      input = io.StdIn.readLine()
      test = tui.processInput(input)

    } while(input != "end")
    }

}
