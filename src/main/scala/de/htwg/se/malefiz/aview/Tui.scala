package de.htwg.se.malefiz.aview

import de.htwg.se.malefiz.controller.{Controller}
import de.htwg.se.malefiz.model.{Cube, Player}
import de.htwg.se.malefiz.util.Observer


class Tui(controller: Controller) extends Observer {


  def processInput(input: String)  : String = {
    input match {
      case "new game" => controller.createGameBoardGraph
      val x =  controller.gameBoardToString

    }
  }

  override def update: Unit = println(controller.gameBoardToString)
}
