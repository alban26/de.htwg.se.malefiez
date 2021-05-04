package de.htwg.se.malefiz.gameBoardModule.aview

import de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent.{ControllerInterface, GameBoardChanged}

import scala.swing.Reactor

class Tui(controller: ControllerInterface) extends Reactor {

  listenTo(controller)

  def runInput(input: String): Unit =
    controller.checkInput(input).fold(l => println(l), r => processInput(input))

  def processInput(input: String): Unit =
    input match {
      case "undo" => controller.undo()
      case "saveJson" => controller.saveAsJson()
      case "saveXML" => controller.saveAsXML()
      case "load" => controller.load()
      case "loadFromDB" => controller.loadFromDB()
      case "redo" => controller.redo()
      case _ =>
        controller.execute(input)
        textPrint("-------")
    }

  reactions += {
    case _: GameBoardChanged => update()
  }

  def update(): Unit = {
    textPrint(controller.gameBoardToString.get)
  }

  def textPrint(str: String): Unit = println(str)
}
