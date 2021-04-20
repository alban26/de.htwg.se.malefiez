package de.htwg.se.malefiz.aview

import de.htwg.se.malefiz.controller.controllerComponent.ControllerInterface

class Tui(controller: ControllerInterface) {

  def runInput(input: String): Unit = {
    input match {
      case "load" => controller.load()
      case "start" => controller.startGame()
      case _ => controller
        .checkInput(input)
        .fold(error => println(error), success => controller.execute(success))
    }
  }

}
