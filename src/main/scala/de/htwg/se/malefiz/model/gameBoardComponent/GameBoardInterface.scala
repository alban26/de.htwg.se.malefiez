package de.htwg.se.malefiz.model.gameBoardComponent

import de.htwg.se.malefiz.controller.controllerComponent.GameStates.GameState
import de.htwg.se.malefiz.controller.controllerComponent.Statements.Statements
import de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl.{Cell, GameBoard}
import de.htwg.se.malefiz.model.playerComponent.Player

import scala.collection.mutable.Map
import scala.util.Try

trait GameBoardInterface {

  def cellList: List[Cell]

  def players: List[Player]

  def gameBoardGraph: Map[Int, Set[Int]]

  def possibleCells: Set[Int]

  def dicedNumber: Option[Int]

  def playersTurn: Option[Player]

  def selectedFigure: Option[(Int, Int)]

  def stateNumber: Option[Int]

  def statementStatus: Option[Statements]


  def returnGameBoardAsString(): String

  def execute(callback: Int => GameBoard, y: Int): GameBoard

  def setPlayersTurn(player: Option[Player]): GameBoard

  def setSelectedFigure(playerNumber: Int, figureNumber: Int): GameBoard

  def setStateNumber(stateNumber: Int): GameBoard

  def setStatementStatus(statement: Statements): GameBoard

  def setPossibleCell(pC: Set[Int]): GameBoard

  def rollDice(): GameBoard

  def setDicedNumber(dicedNumber: Int): GameBoard

  def clearPossibleCells: GameBoard

  def getPossibleCells(startCellNumber: Int, cube: Int): GameBoard


  def setPosiesCellTrue(n: List[Int]): GameBoard

  def setPossibleCell1True(m: Int, n: List[Int], lis: List[Cell]): List[Cell]

  def setPossibleCellTrue(cellNumber: Int): Cell


  def setPosiesCellFalse(cellList: List[Int]): GameBoard

  def setPossibleCell1False(m: Int, n: List[Int], lis: List[Cell]): List[Cell]

  def setPossibleCellFalse(cellNumber: Int): Cell


  def removeActualPlayerAndFigureFromCell(playerNumber: Int, figureNumber: Int): GameBoard

  def removePlayerFigureOnCell(cellNumber: Int): Cell

  def removePlayerOnCell(cellNumber: Int): Cell


  def setWall(cellNumber: Int): GameBoard

  def updateListWall(cellNumber: Int): List[Cell]

  def placeWall(cellNumber: Int): Cell


  def removeWall(cellNumber: Int): GameBoard

  def removeListWall(cellNumber: Int): List[Cell]

  def setHasWallFalse(cellNumber: Int): Cell


  def createPlayer(text: String): GameBoard

  def nextPlayer(list: List[Player], n: Int): Option[Player]

  def setFigure(figureNumber: Int, cellNumber: Int): GameBoard

  def setPlayerFigureOnCell(figureNumber: Int, cellNumber: Int): Cell

  def getPlayerFigure(playerNumber: Int, figureNumber: Int): Int

  def getHomeNr(playerNumber: Int, figureNumber: Int): Int

  def setPlayer(playerNumber: Int, cellNumber: Int): GameBoard

  def setPlayerOnCell(playerNumber: Int, cellNumber: Int): Cell

  def setPossibilitiesTrueOrFalse(boolean: Boolean)(cellNumber: Int): Cell

  def setPosiesTrueOrFalse(cellNumber: Int, state: GameState): GameBoard

  def setPossibleFiguresTrueOrFalse(cellListLength: Int, cellNumber: Int, cellList: List[Cell], state: GameState): List[Cell]

  def setPossibleFigures(cellListLength: Int, cellNumber: Int, cellList: List[Cell])(function: Int => Cell): List[Cell]

}

trait DiceInterface {

  def rollDice: Option[Int]

}

trait CreatorInterface {

  def readTextFile(filename: String): Try[Iterator[String]]

  def getCellList(inputFile: String): List[Cell]

  def getCellGraph(fileInput: String): Map[Int, Set[Int]]

  def updateCellGraph(key: Int, value: Int, map: Map[Int, Set[Int]]): Map[Int, Set[Int]]

}
