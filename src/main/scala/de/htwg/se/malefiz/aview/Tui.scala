package de.htwg.se.malefiz.aview

import de.htwg.se.malefiz.controller.Controller
import de.htwg.se.malefiz.util.Observer


class Tui(controller: Controller) extends Observer {

  def processInput(input: String) : Unit = {

    input match {
      case "new game" => controller.gameBoard
        update
      case "2 Player" => controller.createPlayers(2)
      case "3 Player" => controller.createPlayers(3)
      case "4 Player" => controller.createPlayers(4)
      case "roll cube" =>

        val a = controller.rollCube
        val b = controller.getSet(controller.getFigure(1, 5), a)

        println("geworfene Zahl" + a)

        println()
        b.foreach(x => println(x))

      case "stats" => printFigures()
      case _ => input.split(" ").toList.map(c => c.toInt) match {
            case player :: playerFigure :: cell :: Nil =>
              controller.setPlayerFigure(player, playerFigure, cell)
              update
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

  override def update: Unit = println(controller.gameBoardToString)
  def textPrint(str: String): Unit = println(update + str)

}
