package de.htwg.se.malefiz.gameBoardModule.aview

import de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent.{ControllerInterface, GameBoardChanged}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}
import scala.swing.Reactor

class Tui(controller: ControllerInterface) extends Reactor {

  listenTo(controller)

  def runInput(input: String): Unit =
    controller.checkInput(input).fold(l => println(l), r => processInput(input))

  def processInput(input: String): Unit = {
      input match {
        case "undo" => controller.undo()
        case "saveJson" => controller.saveAsJson()
        case "saveXML" => controller.saveAsXML()
        case "saveDB" => controller.saveInDb()
        case "load" => controller.load()
        case "loadFromDB" => controller.loadFromDB()
        case "redo" => controller.redo()
        case _ =>
          controller.execute(input)
          textPrint("-------")
      }
  }

  def processPlayers(players: List[String]): Future[Unit] = {
      Future(players.foreach(p => processInput("n " + p)))
  }


  reactions += {
    case _: GameBoardChanged => update()
  }

  def update(): Unit = {
    textPrint(controller.gameBoardToString.get)
  }

  def textPrint(str: String): Unit = println(str)
}
