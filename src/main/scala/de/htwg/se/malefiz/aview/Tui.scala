package de.htwg.se.malefiz.aview

import de.htwg.se.malefiz.controller.controllerComponent.GameBoardChanged
import de.htwg.se.malefiz.controller.controllerComponent.GameBoardChanged
import de.htwg.se.malefiz.controller.controllerComponent.controllerBaseImpl.Controller

import scala.swing.Reactor


class Tui(controller: Controller) extends Reactor {

  listenTo(controller)

  def processInput1(input: String): Unit = {
    update
    input match {
      case "z" => controller.undo
      case "y" => controller.redo
      case "exit" => System.exit(0)
      case _ =>
        controller.execute(input)
        textPrint("-------")
        textPrint(controller.gameBoard.players.mkString("\n"))
    }
  }

  reactions += {
    case event: GameBoardChanged => update
  }

  def update: Unit = {
    textPrint(controller.gameBoardToString)
  }

  def textPrint(str: String): Unit = println(str)

}


