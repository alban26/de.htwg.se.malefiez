package de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent

import de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent.GameStates.GameState
import de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent.Statements.Statements
import de.htwg.se.malefiz.gameBoardModule.model.gameBoardComponent.GameBoardInterface
import de.htwg.se.malefiz.gameBoardModule.model.gameBoardComponent.gameBoardBaseImpl.{GameBoard, Player}

import scala.concurrent.Future
import scala.swing.{Button, Publisher}
import scala.swing.event.Event

trait ControllerInterface extends Publisher {

  def loadFromDB(): Unit

  def saveInDb(): Unit

  def gameBoard: GameBoardInterface

  def gameBoardToString: Option[String]

  def resetGameBoard(): Unit

  def setGameBoard(gameBoard: GameBoardInterface): Unit

  def undo(): Unit

  def redo(): Unit

  def weHaveAWinner(): Unit

  def rollCube: Option[Int]

  def setDicedNumber(dicedNumber: Option[Int]): Unit

  def createPlayer(name: String): Unit

  def nextPlayer(playerList: List[Option[Player]], playerNumber: Int): Option[Player]

  def setPlayersTurn(player: Option[Player]): Unit

  def placePlayerFigure(playerNumber: Int, playerFigure: Int, cellNumber: Int): Unit

  def setSelectedFigure(playerNumber: Int, figureNumber: Int): Unit

  def getFigurePosition(playerNumber: Int, figureNumber: Int): Int

  def resetPossibleCells(): Unit

  def setStateNumber(n: Int): Unit

  def calculatePath(startCell: Int, diceNumber: Int): Unit

  def removeActualPlayerAndFigureFromCell(playerNumber: Int, figureNumber: Int): Unit

  def placeFigure(figureNumber: Int, cellNumber: Int): Unit

  def placePlayer(playerNumber: Int, cellNumber: Int): Unit

  def placeOrRemoveWall(n: Int, boolean: Boolean): Unit

  def getGameState: GameState

  def setStatementStatus(statement: Statements): Unit

  def setPossibleCells(possibleCells: Set[Int]): GameBoardInterface

  def setPossibleFiguresTrueOrFalse(playerNumber: Int): Unit

  def setPossibleCellsTrueOrFalse(toList: List[Int]): Unit

  def execute(string: String): Unit

  def checkInput(input: String): Either[String, String]

  def saveAsJson(): Unit

  def saveAsXML(): Unit

  def load(): Unit

  def evalJson(gameString: String): Future[Unit]

  def evalXml(result: String): Unit

  //def loadGameBoardXml(result: String): GameBoardInterface

  def loadGameBoardJson(result: String): GameBoard

}

class GameBoardChanged extends Event

case class ButtonClicked(source: Button) extends Event

class ChangeWall extends Event

class Winner extends Event
