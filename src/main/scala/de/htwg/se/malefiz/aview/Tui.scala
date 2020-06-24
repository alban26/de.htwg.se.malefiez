package de.htwg.se.malefiz.aview

import de.htwg.se.malefiz.Malefiz.cellConfigFile
import de.htwg.se.malefiz.controller.{Controller, GameBoardChanged}
import de.htwg.se.malefiz.controller.GameStates.GameState._
import de.htwg.se.malefiz.model.{Cell, Creator, Player}
import de.htwg.se.malefiz.util.Observer
import javax.swing.plaf.basic.BasicBorders.RolloverButtonBorder

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


