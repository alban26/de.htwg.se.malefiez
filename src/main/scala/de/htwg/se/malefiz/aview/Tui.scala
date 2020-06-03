package de.htwg.se.malefiz.aview

import de.htwg.se.malefiz.controller.Controller
import de.htwg.se.malefiz.model.{Cube, Player}
import de.htwg.se.malefiz.util.Observer

import scala.io

class Tui(controller: Controller) extends Observer {
  val sourceFile =
//controller.add(this)
  def processInput(input: String)  : String = {
    input match {
      case "new game" => controller.createNewGameBoard()

    }
  }
}
