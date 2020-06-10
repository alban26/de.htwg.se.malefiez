package de.htwg.se.malefiz.aview

import de.htwg.se.malefiz.Malefiz.cellList1
import de.htwg.se.malefiz.controller.Controller
import de.htwg.se.malefiz.model.{Cube, GameBoard, Player, SetupState}
import de.htwg.se.malefiz.util.Observer


class Tui(controller: Controller) extends Observer {



  def setupTUI(n: Int): Unit = {
    n match {
      case 0 => {
        textPrint("Wie viele Spieler seit ihr? ")
      }
      case 1 => {
        textPrint("Gebt eure Namen ein!")
      }
    }
  }


  def processInput(input: String) : Unit = {
    input match {
      case  "3" => controller.gameBoard
      case _ => val s = input.split(" ").toList
        controller.setupFigures(s)
        update
    }
  }



  override def update: Unit = println(controller.gameBoardToString)
  def textPrint(str: String): Unit = println(str)
}
