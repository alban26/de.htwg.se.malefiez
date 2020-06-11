package de.htwg.se.malefiz.aview

import de.htwg.se.malefiz.controller.Controller
import de.htwg.se.malefiz.util.Observer


class Tui(controller: Controller) extends Observer {

  def processInput(input: String) : Unit = {

    input match {
      case "q" =>
      //case "2 Player" => controller.createPlayers(2)
      //case "3 Player" => controller.createPlayers(3)
      //case "4 Player" => controller.createPlayers(4)
      case "roll cube" =>

        val a = controller.rollCube
        val b = controller.getPCells(controller.getFigure(1, 5), a)

        println("geworfene Zahl" + a)

        println()
        b.foreach(x => println(x))

      case "stats" => printFigures()
      case _ =>
        val inputList = input.split(" ").toList.map(c => c.toInt)
        inputList.length match {
          case 1 => controller.setWall(inputList.head)
            update
          case 3 => controller.setPlayerFigure(inputList.head, inputList(1), inputList(2))
        }
    }
  }

  def printFigures() : Unit = {
    for(i <- 1 to 4) {
      for (y <- 1 to 5) {
        println("Spieler " + i + " Figur " + y + " steht auf Feld " + controller.getFigure(i, y))
      }
    }
  }

  override def update: Boolean = {
    println(controller.gameBoardToString)
    true
  }

  def textPrint(str: String): Unit = println(update + str)

}
