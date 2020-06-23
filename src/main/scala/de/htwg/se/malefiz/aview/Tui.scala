package de.htwg.se.malefiz.aview

import de.htwg.se.malefiz.Malefiz.cellConfigFile
import de.htwg.se.malefiz.controller.{Controller, GameBoardChanged, GameState, PlayingState}
import de.htwg.se.malefiz.controller.GameState.GameState
import de.htwg.se.malefiz.model.{Cell, Creator}
import de.htwg.se.malefiz.util.Observer
import javax.swing.plaf.basic.BasicBorders.RolloverButtonBorder

import scala.swing.Reactor


class Tui(controller: Controller) extends Reactor {

  listenTo(controller)

  def setupPlayers(input: String): Unit = {
    val list = input.split(" ").toList
    controller.player = controller.createPlayer(list.length-1, list)
    controller.gameState = GameState.PLAYERS_TURN
    controller.playersTurn = controller.player(0)
    update
  }

  def input(input: String): Unit = {
    textPrint("Welcome to Malefiz")
    controller.gameState match {
      case GameState.ENTRY_NAMES => setupPlayers(input)
      case GameState.PLAYERS_TURN => processInput(input)
    }
  }

  def processInput(input: String): Unit = {
    controller.playingState match {

      case PlayingState.ROLL => controller.dicedNumer = controller.rollCube
        controller.setPosisTrue(controller.playersTurn.playerNumber)

      case PlayingState.SELECT_PLAYER => input match {
        case _ => input.toList.filter(c => c != ' ').filter(_.isDigit).map(c => c.toString.toInt) match {
          case player :: figure :: Nil => controller.getPCells(controller.getFigure(player,figure), controller.dicedNumer)
            controller.setPosisFalse(controller.playersTurn.playerNumber)
            update
      }
    }
      case PlayingState.SET_PLAYER => processInput1(input)
    }
  }


  def processInput1(input: String) : Unit = {

    input match {
      case "z" => controller.undo
        update
      case "y" => controller.redo
        update
      case _ =>


        val inputList = input.split(" ").toList.map(c => c.toInt)
        inputList.length match {
          case 1 => controller.setWall(inputList(0))
            update
          case 2 =>
            controller.getPCells(inputList(0), inputList(1))
            update
          case 3 => controller.setPlayerFigure(inputList.head, inputList(1), inputList(2))
            update


        }
    }
  }


  reactions += {
    case event: GameBoardChanged => update
  }


  def update: Boolean = {
    textPrint(controller.gameBoardToString)
    textPrint(controller.player.mkString("\n"))
    textPrint("--------------------------")
    textPrint(controller.playersTurn.toString + " ist dran")
    val dice = if(controller.dicedNumer == 0) "" else controller.dicedNumer
    textPrint("Gewürfelt: "+ dice)
    //textPrint(PlayingState.message(controller.playingState))
    textPrint("Du kannst auf folgende Felder: "+ controller.gameBoard.possibleCells.toString().mkString(""))
    true
  }

  def textPrint(str: String): Unit = println(str)

}
