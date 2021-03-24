package de.htwg.se.malefiz.model.gameBoardComponent

import de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl.{Cell, GameBoard}
import de.htwg.se.malefiz.model.playerComponent.Player
import scala.collection.mutable.Map
import de.htwg.se.malefiz.controller.controllerComponent.Statements.Statements
import scala.util.Try

trait GameBoardInterface {

  def cellList: List[Cell]
  def players: List[Player]
  def gameBoardGraph: Map[Int, Set[Int]]
  def possibleCells: Set[Int]
  def dicedNumber: Int
  def playersTurn: Option[Player]
  def selectedFigure: Option[(Int, Int)]
  def stateNumber: Option[Int]
  def statementStatus: Option[Statements]

  //++++++++++++++++++++++++++++++++

  def clearPossibleCells: GameBoard

  def s(n: Int): Int

  def buildPlayerString(list: List[Cell]): String

  def buildString(list: List[Cell]): String

  def createStringValues(list: List[Cell], n: Int, O: Int, z: Int, i: Int, l: Int): String

  def createString(
      list: List[Cell],
      n: Int,
      sliceBeginU: Int,
      sliceEndU: Int,
      sliceBeginO: Int,
      sliceEndO: Int,
      gapLeftO: String,
      gapLeftU: String,
      gapBetween: String,
      z: Int,
      i: Int,
      l: Int
  ): String

  def removePlayerFigureOnCell(cellNumber: Int): Cell

  def removePlayerOnCell(cellNumber: Int): Cell

  def setPlayerFigureOnCell(figureNumber: Int, cellNumber: Int): Cell

  def setPlayerOnCell(playerNumber: Int, cellNumber: Int): Cell

  def removeActualPlayerAndFigureFromCell(playerNumber: Int, figureNumber: Int): GameBoard

  def setFigure(figureNumber: Int, cellNumber: Int): GameBoard

  def setPlayer(playerNumber: Int, cellNumber: Int): GameBoard

  def getHomeNr(playerNumber: Int, figureNumber: Int): Int

  def getPlayerFigure(playerNumber: Int, figureNumber: Int): Int

  def getPossibleCells(startCellNumber: Int, cube: Int): GameBoard

  def setHasWallFalse(cellNumber: Int): Cell

  def removeWall(cellNumber: Int): GameBoard

  def removeListWall(cellNumber: Int): List[Cell]

  def placeWall(cellNumber: Int): Cell

  def setWall(cellNumber: Int): GameBoard

  def updateListWall(cellNumber: Int): List[Cell]

  def setPosiesCellTrue(n: List[Int]): GameBoard

  def setPossibleCell1True(m: Int, n: List[Int], lis: List[Cell]): List[Cell]

  def setPossibleCellTrue(cellNumber: Int): Cell

  def setPosiesCellFalse(cellList: List[Int]): GameBoard

  def setPossibleCell1False(m: Int, n: List[Int], lis: List[Cell]): List[Cell]

  def setPossibleCellFalse(cellNumber: Int): Cell

  def setPosiesTrue(cellNumber: Int): GameBoard

  def setPossibilitiesTrue(cellNumber: Int): Cell

  def setPossibleFiguresTrue(m: Int, n: Int, lis: List[Cell]): List[Cell]

  def setPosiesFalse(cellNumber: Int): GameBoard

  def setPossibilitiesFalse(cellNumber: Int): Cell

  def setPossibleFiguresFalse(m: Int, n: Int, lis: List[Cell]): List[Cell]

  def execute(callback: Int => GameBoard, y: Int): GameBoard

  def nextPlayer(list: List[Player], n: Int): Option[Player]

  def createPlayer(text: String): GameBoard

  def createGameBoard(): String

  def setPossibleCell(pC: Set[Int]): GameBoard

  def setDicedNumber(dicedNumber: Int): GameBoard

  def setPlayersTurn(player: Option[Player]): GameBoard

  def setSelectedFigure(playerNumber: Int, figureNumber: Int): GameBoard

  def setStateNumber(stateNumber: Int): GameBoard

  def setStatementStatus(statement: Statements): GameBoard

}

trait CubeInterface {

  def getRandomNumber: Int

}

trait CreatorInterface {

  def readTextFile(filename: String): Try[Iterator[String]]

  def getCellList(inputFile: String): List[Cell]

  def getCellGraph(fileInput: String): Map[Int, Set[Int]]

  def updateCellGraph(key: Int, value: Int, map: Map[Int, Set[Int]]): Map[Int, Set[Int]]

}
