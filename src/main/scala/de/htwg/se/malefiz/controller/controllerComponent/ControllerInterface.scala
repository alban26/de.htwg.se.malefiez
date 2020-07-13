package de.htwg.se.malefiz.controller.controllerComponent

import de.htwg.se.malefiz.controller.controllerComponent.GameStates.GameState
import de.htwg.se.malefiz.controller.controllerComponent.Statements.Statements
import de.htwg.se.malefiz.model.gameBoardComponent.GameBoardInterface
import de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl.Cell
import de.htwg.se.malefiz.model.playerComponent.Player
import scala.swing.Publisher

trait ControllerInterface extends Publisher {

  def resetPossibleCells(): Unit

  def setStateNumber(n: Int): Unit

  def getStateNumber: Int

  def execute(string: String): Unit

  def createPlayer(name: String): Unit

  def setPossibleCellsTrue(l: List[Int]): Unit

  def setPossibleCellsFalse(l: List[Int]): Unit

  def setPossibleFiguresTrue(n: Int): Unit

  def setPossibleFiguresFalse(n: Int): Unit

  def rollCube: Int

  def setPlayerFigure(playerNumber: Int, playerFigure: Int, cellNumber: Int): Unit

  def getFigure(playerNumber: Int, figureNumber: Int): Int

  def calculatePath(startCell: Int, cubeNumber: Int): Unit

  def removeActualPlayerAndFigureFromCell(playerNumber: Int, figureNumber: Int): Unit

  def setFigure(fN: Int, cN: Int): Unit

  def setPlayer(pN: Int, cN: Int): Unit

  def setWall(n: Int): Unit

  def removeWall(n: Int): Unit

  def gameBoardToString: String

  def undo(): Unit

  def redo(): Unit

  def resetGameBoard(): Unit

  def weHaveAWinner() : Unit

  def nextPlayer(list: List[Player], n: Int): Player

  def getCellList: List[Cell]

  def getPlayer: List[Player]

  def getPossibleCells: Set[Int]

  def getDicedNumber: Int

  def getPlayersTurn: Player

  def getSelectedFigure: (Int, Int)

  def getGameState: GameState

  def getStatement: Statements

  def setSelectedFigures(n: Int, m: Int): Unit

  def setStatementStatus(statements: Statements): Unit

  def setPlayersTurn(player: Player): Unit

  def setDicedNumber(n: Int): Unit

  def save(): Unit

  def load(): Unit

  def setGameBoard(gb: GameBoardInterface)

  def getGameBoard: GameBoardInterface

  def setPossibleCells(pC: Set[Int]) : GameBoardInterface

}

import scala.swing.Button
import scala.swing.event.Event

class GameBoardChanged extends Event
case class ButtonClicked(source: Button) extends Event
class ChangeWall extends Event
class Winner extends Event

