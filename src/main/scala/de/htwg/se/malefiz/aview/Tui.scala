package de.htwg.se.malefiz.aview

import de.htwg.se.malefiz.controller.{Controller}
import de.htwg.se.malefiz.model.{Cube, Player}
import de.htwg.se.malefiz.util.Observer


class Tui(controller: Controller) extends Observer {


  def processInput(input: String) : Unit = {
    input match {
      case "new game" => controller.gameBoard
        textPrint("Wie viele Spieler seit Ihr? ")



    }
  }



  override def update: Unit = println(controller.gameBoardToString)
  def textPrint(str: String): Unit = println(update + str)
}
