package de.htwg.se.malefiz.aview

import de.htwg.se.malefiz.Malefiz.cellConfigFile
import de.htwg.se.malefiz.controller.Controller
import de.htwg.se.malefiz.controller.GameStates.GameState._
import de.htwg.se.malefiz.model.{Cell, Creator, Player}
import de.htwg.se.malefiz.util.Observer

import javax.swing.plaf.basic.BasicBorders.RolloverButtonBorder


class Tui(controller: Controller) extends Observer {

  controller.add(this)


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


  override def update: Boolean = {
    textPrint(controller.gameBoardToString)
    true
  }

  def textPrint(str: String): Unit = println(str)

}


