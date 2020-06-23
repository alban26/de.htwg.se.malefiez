package de.htwg.se.malefiz.controller

import de.htwg.se.malefiz.controller.GameStates.GameState
import de.htwg.se.malefiz.controller.GameStates.GameState._
//import de.htwg.se.malefiz.controller.PlayingState._
import de.htwg.se.malefiz.util.{Observable, Observer, UndoManager}
import de.htwg.se.malefiz.model.{Cell, Creator, Cube, GameBoard, Player}


class Controller(var gameBoard: GameBoard) extends Observable {

  var playersTurn: Player = _
  var dicedNumber: Int = _
  var selectedFigure: (Int, Int) = _

  val s: GameState = GameState(this)
  val undoManager = new UndoManager

  def execute(string: String): Boolean = {
    s.run(string)
    true
  }


  def createPlayer(name: String): Unit = {
    gameBoard = gameBoard.createPlayer(name)
    notifyObservers
  }


  def setPosisCellTrue(l: List[Int]): Unit = {
    gameBoard = gameBoard.setPosiesCellTrue(l)
    notifyObservers
  }

  def setPosisCellFalse(l: List[Int]): Unit = {
    gameBoard = gameBoard.setPosiesCellFalse(l)
    notifyObservers
  }

  def setPosisTrue(n: Int): Unit = {
    gameBoard = gameBoard.execute(gameBoard.setPosiesTrue,n)
    notifyObservers
  }

  def setPosisFalse(n: Int): Unit = {
    gameBoard = gameBoard.execute(gameBoard.setPosiesFalse,n)
    notifyObservers
  }

  def rollCube: Int = {
    //playingState = SELECT_PLAYER
    Cube().getRandomNumber
  }

  def setPlayerFigure(playerNumber: Int, playerFigure: Int, cellNumber: Int): Unit = {
    undoManager.doStep(new SetPlayerCommand(playerNumber,playerFigure,cellNumber,this))
    notifyObservers
  }

  def getFigure(pn: Int, fn:Int) : Int = {
    val position = gameBoard.getPlayerFigure(pn, fn)
    position
  }

  def getPCells(startCell: Int, cubeNumber: Int) : Unit = {
    gameBoard = gameBoard.getPossibleCells(startCell, cubeNumber)
    //playingState = SET_PLAYER
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
    //gameBoard = gameBoard.execute(gameBoard.setWall,n)
    undoManager.doStep(new SetWallCommand(n,this))
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
