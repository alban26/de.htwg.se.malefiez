package de.htwg.se.malefiz.controller

import de.htwg.se.malefiz.controller.GameState._
import de.htwg.se.malefiz.controller.PlayingState._
import de.htwg.se.malefiz.util.{Observable, Observer, UndoManager}
import de.htwg.se.malefiz.model.{Cell, Creator, Cube, GameBoard, Player}


class Controller(var gameBoard: GameBoard) extends Observable {

  var gameState: GameState = ENTRY_NAMES
  var playingState: PlayingState = ROLL
  var playersTurn: Player = _
  var player: List[Player] = List.empty
  var dicedNumer: Int = _
  var selectedPlayer: Tuple2[Int, Int] = _

  private val undoManager = new UndoManager


  def createPlayer(n: Int, name: List[String]): List[Player] = {
    if (n == 0) {
      List(Player(n+1, name(n)))
    } else {
      createPlayer(n - 1, name) :+ Player(n+1, name(n))
    }
  }


  def setPosis(n: Int): Unit = {
    gameBoard = gameBoard.setPosies(n)
    notifyObservers
  }

  def setPosisFalse(n: Int): Unit = {
    gameBoard = gameBoard.setPosiesFalse(n)
    notifyObservers
  }

  def rollCube: Int = {
    playingState = SELECT_PLAYER
    Cube().getRandomNumber
  }

  def setPlayerFigure(playerNumber: Int, playerFigure: Int, cellNumber: Int): Unit = {
    undoManager.doStep(new SetPlayerCommand(playerNumber,playerFigure,cellNumber,this))
  }

  def getFigure(pn: Int, fn:Int) : Int = {
    val position = gameBoard.getPlayerFigure(pn, fn)
    position
  }

  def getPCells(startCell: Int, cubeNumber: Int) : Unit = {
    gameBoard = gameBoard.getPossibleCells(startCell, cubeNumber)
    playingState = SET_PLAYER
    notifyObservers
  }

  def removeActualPlayerAndFigureFromCell(pN: Int, fN: Int): Unit = {
    gameBoard = gameBoard.removeActualPlayerAndFigureFromCell(pN,fN)
    notifyObservers
  }

  def setFigure(fN: Int, cN: Int): Unit = {
    gameBoard = gameBoard.setFigure(fN,cN)
    notifyObservers
  }

  def setPlayer(pN: Int, cN: Int): Unit = {
    gameBoard = gameBoard.setPlayer(pN, cN)
    notifyObservers
  }


  def setWall(n: Int): Unit = {
    gameBoard = gameBoard.setWall(n)
    notifyObservers
  }

  def rWall(n: Int): Unit = {
    gameBoard = gameBoard.rWall(n)
    notifyObservers
  }
  def gameBoardToString: String = gameBoard.createGameBoard()

  def undo: Unit = {
    undoManager.undoStep
    notifyObservers
  }

  def redo: Unit = {
    undoManager.redoStep
    notifyObservers
  }

}
