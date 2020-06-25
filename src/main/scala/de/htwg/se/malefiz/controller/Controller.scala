package de.htwg.se.malefiz.controller

import de.htwg.se.malefiz.aview.gui.SwingGui
import de.htwg.se.malefiz.controller.GameStates.GameState
import de.htwg.se.malefiz.util.{Observable, UndoManager}
import de.htwg.se.malefiz.model.{Cube, GameBoard, Player}

import scala.swing.Publisher

class Controller(var gameBoard: GameBoard) extends Publisher {

  var playersTurn: Player = _
  var dicedNumber: Int = _
  var selectedFigure: (Int, Int) = _

  val s: GameState = GameState(this)
  val undoManager = new UndoManager

  var gui = new SwingGui(this)

  def execute(string: String): Boolean = {
    s.run(string)
    true
  }


  def createPlayer(name: String): Unit = {
    gameBoard = gameBoard.createPlayer(name)
    publish(new GameBoardChanged)
  }


  def setPosisCellTrue(l: List[Int]): Unit = {
    gameBoard = gameBoard.setPosiesCellTrue(l)
    publish(new GameBoardChanged)
  }

  def setPosisCellFalse(l: List[Int]): Unit = {
    gameBoard = gameBoard.setPosiesCellFalse(l)
    publish(new GameBoardChanged)
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
    //playingState = SELECT_PLAYER
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
    //playingState = SET_PLAYER
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
