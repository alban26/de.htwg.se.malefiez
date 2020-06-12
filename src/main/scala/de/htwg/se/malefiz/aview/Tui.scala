package de.htwg.se.malefiz.aview

import de.htwg.se.malefiz.controller.Controller
import de.htwg.se.malefiz.util.Observer


class Tui(controller: Controller) extends Observer {

  def processInput(input: String) : Unit = {

    input match {
      case "s" => update
      case _ =>
        val inputList = input.split(" ").toList.map(c => c.toInt)
        inputList.length match {
          case 1 => controller.setWall(inputList.head)
            update
          case 2 =>
            controller.getPCells(inputList.head, inputList(1))
            update
          case 3 => controller.setPlayerFigure(inputList.head, inputList(1), inputList(2))
            update
        }
    }
  }

  override def update: Boolean = {
    println(controller.gameBoardToString)
    true
  }

  def textPrint(str: String): Unit = println(update + str)

}
