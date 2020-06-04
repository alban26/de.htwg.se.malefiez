package de.htwg.se.malefiz.aview

import de.htwg.se.malefiz.controller.Controller
import de.htwg.se.malefiz.model.{Cell, Cube, Player}
import de.htwg.se.malefiz.util.Observer


class Tui(controller: Controller) extends Observer {

  val graphConfig = "/Users/robert/IdeaProjects/de.htwg.se.malefiz/src/main/scala/de/htwg/se/malefiz/model/mainCellLinks"

  def processInput(input: String) : Unit = {

    input match {
      case "new game" => controller.gameBoard.toString
        update
      case "2 Player" => controller.createPlayers(2)
      case "3 Player" => controller.createPlayers(3)
      case "4 Player" => controller.createPlayers(4)
      case "roll cube" =>
        textPrint(controller.rollCube.toString)
      case "stats" => controller.showPlayer()

      case _ => input.toList.filter(c => c != ' ').map(c => c.toString.toInt) match {
        case player :: playerFigure :: cell :: Nil =>
          controller.setPlayerFigure(player, playerFigure, cell)
          update
        case _ => update
      }
    }
  }

  override def update: Unit = println(controller.gameBoardToString)
  def textPrint(str: String): Unit = println(update + str)
}
