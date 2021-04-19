package de.htwg.se.malefiz.aview

import de.htwg.se.malefiz.controller.controllerComponent.{
  ControllerInterface,
  GameBoardChanged
}
import scala.swing.Reactor

class Tui(controller: ControllerInterface) extends Reactor {


  def runInput(input: String): Unit = {
    input match {

      case "load" => controller.load()
      case "start" => controller.startGame()
      case _ => controller
        .checkInput(input)
        .fold(l => println(l), r => controller.execute(input))
    }
  }
}
