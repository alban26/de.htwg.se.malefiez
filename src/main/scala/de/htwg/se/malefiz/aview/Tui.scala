package de.htwg.se.malefiz.aview

import de.htwg.se.malefiz.controller.Controller
import de.htwg.se.malefiz.model.{Cube, Player, SetupState}
import de.htwg.se.malefiz.util.Observer


class Tui(controller: Controller) extends Observer {



def setupTUI(input: String, n: Int): Unit = {
  input match {
case
  }
}

/*
  def processInput(input: String) : Unit = {
    input match {
      case "new game" => controller.gameBoard
        update
      case "s" =>
      case "12" => controller.setWall(input.toInt)
      case "3" => controller.setPlayer("Alban")
        update

    }
  }
*/


  override def update: Unit = println(controller.gameBoardToString)
  def textPrint(str: String): Unit = println(update + str)
}
