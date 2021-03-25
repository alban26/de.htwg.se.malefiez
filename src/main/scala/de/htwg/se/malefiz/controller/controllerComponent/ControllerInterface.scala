package de.htwg.se.malefiz.controller.controllerComponent

import de.htwg.se.malefiz.controller.controllerComponent.GameStates.GameState
import de.htwg.se.malefiz.controller.controllerComponent.Statements.Statements
import de.htwg.se.malefiz.model.gameBoardComponent.GameBoardInterface
import de.htwg.se.malefiz.model.playerComponent.Player

import scala.swing.Publisher

trait ControllerInterface extends Publisher {

  def execute(string: String): Unit

  def gameBoard: GameBoardInterface

  def resetPossibleCells(): Unit

  def setStateNumber(n: Int): Unit

  def createPlayer(name: String): Unit

  def setPossibleCellsTrue(availableCells: List[Int]): Unit

  def setPossibleCellsFalse(l: List[Int]): Unit

  def setPossibleFiguresTrue(n: Int): Unit

  def setPossibleFiguresFalse(n: Int): Unit

  def setPlayerFigure(playerNumber: Int, playerFigure: Int, cellNumber: Int): Unit

  def getFigurePosition(playerNumber: Int, figureNumber: Int): Int

  def calculatePath(startCell: Int, diceNumber: Int): Unit

  def removeActualPlayerAndFigureFromCell(playerNumber: Int, figureNumber: Int): Unit

  def setFigure(figureNumber: Int, cellNumber: Int): Unit

  def setPlayer(playerNumber: Int, cellNumber: Int): Unit

  def setWall(cellNumber: Int): Unit

  def removeWall(cellNumber: Int): Unit

  def gameBoardToString: String

  def undo(): Unit

  def redo(): Unit

  def resetGameBoard(): Unit

  def weHaveAWinner(): Unit

  def nextPlayer(playerList: List[Player], playerNumber: Int): Option[Player]

  def setSelectedFigure(playerNumber: Int, figureNumber: Int): Unit

  def getGameState: GameState

  def setStatementStatus(statement: Statements): Unit

  def save(): Unit

  def load(): Unit

  def setGameBoard(gameBoard: GameBoardInterface): Unit

  def getGameBoard: GameBoardInterface

  def setPossibleCells(possibleCells: Set[Int]): GameBoardInterface

  def setPlayersTurn(player: Option[Player]): Unit

  def rollCube: Option[Int]

  def setDicedNumber(dicedNumber: Int): Unit
}

import scala.swing.Button
import scala.swing.event.Event

class GameBoardChanged extends Event

case class ButtonClicked(source: Button) extends Event

class ChangeWall extends Event

class Winner extends Event
