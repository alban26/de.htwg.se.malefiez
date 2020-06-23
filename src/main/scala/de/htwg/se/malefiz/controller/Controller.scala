package de.htwg.se.malefiz.controller

import de.htwg.se.malefiz.controller.GameState._
import de.htwg.se.malefiz.controller.PlayingState._
import de.htwg.se.malefiz.util.{Observable, Observer, UndoManager}
import de.htwg.se.malefiz.model.{Cell, Creator, Cube, GameBoard, Player}

import scala.swing.Publisher

class Controller(var gameBoard: GameBoard) extends Publisher {

  var gameState: GameState = ENTRY_NAMES
  var playingState: PlayingState = ROLL
  var playersTurn: Player = _
  var player: List[Player] = List.empty
  var dicedNumer: Int = _

  private val undoManager = new UndoManager

  def createPlayer(n: Int, name: List[String]): List[Player] = {
    if (n == 0) {
      List(Player(n+1, name(n)))
    } else {
      createPlayer(n - 1, name) :+ Player(n+1, name(n))
    }
  }

  def setPosisTrue(n: Int): Unit = {
    gameBoard = gameBoard.execute(gameBoard.setPosiesTrue,n)
    publish(new GameBoardChanged)
  }

  def setPosisFalse(n: Int): Unit = {
    gameBoard = gameBoard.execute(gameBoard.setPosiesFalse,n)
    publish(new GameBoardChanged)
  }

  def rollCube: Int = {
    playingState = SELECT_PLAYER
    Cube().getRandomNumber
  }

  def setPlayerFigure(playerNumber: Int, playerFigure: Int, cellNumber: Int): Unit = {
    undoManager.doStep(new SetPlayerCommand(playerNumber,playerFigure,cellNumber,this))
    publish(new GameBoardChanged)
  }

  def getFigure(pn: Int, fn:Int) : Int = {
    val position = gameBoard.getPlayerFigure(pn, fn)
    position
  }

  def getPCells(startCell: Int, cubeNumber: Int) : Unit = {
    gameBoard = gameBoard.getPossibleCells(startCell, cubeNumber)
    playingState = SET_PLAYER
    publish(new GameBoardChanged)
  }

  def removeActualPlayerAndFigureFromCell(pN: Int, fN: Int): Unit = {
    gameBoard = gameBoard.removeActualPlayerAndFigureFromCell(pN,fN)
    publish(new GameBoardChanged)
  }

  def setFigure(fN: Int, cN: Int): Unit = {
    gameBoard = gameBoard.setFigure(fN,cN)
    publish(new GameBoardChanged)
  }

  def setPlayer(pN: Int, cN: Int): Unit = {
    gameBoard = gameBoard.setPlayer(pN, cN)
    publish(new GameBoardChanged)
  }

  def setWall(n: Int): Unit = {
    //gameBoard = gameBoard.execute(gameBoard.setWall,n)
    undoManager.doStep(new SetWallCommand(n,this))
    publish(new GameBoardChanged)
  }

  def gameBoardToString: String = gameBoard.createGameBoard()

  def undo: Unit = {
    undoManager.undoStep
    publish(new GameBoardChanged)
  }

  def redo: Unit = {
    undoManager.redoStep
    publish(new GameBoardChanged)
  }

}
