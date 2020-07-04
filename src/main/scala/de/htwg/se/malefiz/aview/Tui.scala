package de.htwg.se.malefiz.aview

import de.htwg.se.malefiz.controller.controllerComponent.{ControllerInterface, GameBoardChanged}
import de.htwg.se.malefiz.controller.controllerComponent.controllerBaseImpl.Controller
import javax.inject.Inject

import scala.swing.Reactor


class Tui (controller: ControllerInterface) extends Reactor {

  listenTo(controller)

  def processInput1(input: String): Unit = {
    update
    input match {
      case "z" => controller.undo
      case "s"=> controller.save
      case "load" => controller.load
      case "y" => controller.redo
      case "exit" => System.exit(0)
      case _ =>
        controller.execute(input)
        textPrint("-------")

    }
  }

  reactions += {
    case event: GameBoardChanged => update
  }

  def update: Unit = {
    textPrint(controller.gameBoardToString)
    textPrint(controller.getPlayer.mkString("\n"))
  }

  def textPrint(str: String): Unit = println(str)

}


