package de.htwg.se.malefiz.aview

import de.htwg.se.malefiz.controller.Controller
import de.htwg.se.malefiz.model.{Cell, Cube, Player}
import de.htwg.se.malefiz.util.Observer


class Tui(controller: Controller) extends Observer {


  def processInput(input: String) : Unit = {

    input match {
      case "new game" =>

        controller.gameBoard
        update
      case "2 Player" => controller.createPlayerArray(2)
      case "3 Player" => controller.createPlayerArray(3)
      case "4 Player" => controller.createPlayerArray(4)
      case "roll cube" =>
        textPrint(controller.rollCube.toString)

      case _ => printf("kein Pattern")

    }
  }



  override def update: Unit = println(controller.gameBoardToString)
  def textPrint(str: String): Unit = println(update + str)
}
