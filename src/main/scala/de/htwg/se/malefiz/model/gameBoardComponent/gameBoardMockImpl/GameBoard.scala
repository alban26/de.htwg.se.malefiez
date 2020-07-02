package de.htwg.se.malefiz.model.gameBoardComponent.gameBoardMockImpl

import de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl.Cell
import de.htwg.se.malefiz.model.gameBoardComponent.{GameboardInterface, gameBoardBaseImpl}
import de.htwg.se.malefiz.model.playerComponent.Player

import scala.collection.mutable

case class GameBoard() extends GameboardInterface {
  override def s(n: Int): Int = ???

  override def buildPlayerString(list: List[Cell]): String = ???

  override def buildString(list: List[Cell]): String = ???

  override def createStringValues(list: List[Cell], n: Int, O: Int, z: Int, i: Int, l: Int): String = ???

  override def createString(list: List[Cell], n: Int, sliceBeginU: Int, sliceEndU: Int, sliceBeginO: Int, sliceEndO: Int, gapLeftO: String, gapLeftU: String, gapBetween: String, z: Int, i: Int, l: Int): String = ???

  override def removePlayerFigureOnCell(cN: Int): Cell = ???

  override def removePlayerOnCell(n: Int): Cell = ???

  override def setPlayerFigureOnCell(fN: Int, cN: Int): Cell = ???

  override def setPlayerOnCell(pN: Int, cN: Int): Cell = ???

  override def removeActualPlayerAndFigureFromCell(pN: Int, fN: Int): gameBoardBaseImpl.GameBoard = ???

  override def setFigure(fN: Int, cN: Int): gameBoardBaseImpl.GameBoard = ???

  override def setPlayer(pN: Int, cN: Int): gameBoardBaseImpl.GameBoard = ???

  override def getHomeNr(pN: Int, fN: Int): Int = ???

  override def getPlayerFigure(pN: Int, fN: Int): Int = ???

  override def getPossibleCells(start: Int, cube: Int): gameBoardBaseImpl.GameBoard = ???

  override def removeWall(n: Int): Cell = ???

  override def rWall(n: Int): gameBoardBaseImpl.GameBoard = ???

  override def removeListWall(n: Int): List[Cell] = ???

  override def placeWall(n: Int): Cell = ???

  override def setWall(n: Int): gameBoardBaseImpl.GameBoard = ???

  override def updateListWall(n: Int): List[Cell] = ???

  override def setPosiesCellTrue(n: List[Int]): gameBoardBaseImpl.GameBoard = ???

  override def setPossibleCellTrue(n: Int): Cell = ???

  override def setPossibleCell1True(m: Int, n: List[Int], lis: List[Cell]): List[Cell] = ???

  override def setPosiesCellFalse(n: List[Int]): gameBoardBaseImpl.GameBoard = ???

  override def setPossibleCellFalse(n: Int): Cell = ???

  override def setPossibleCell1False(m: Int, n: List[Int], lis: List[Cell]): List[Cell] = ???

  override def setPosiesTrue(n: Int): gameBoardBaseImpl.GameBoard = ???

  override def setPossibilitiesTrue(n: Int): Cell = ???

  override def setPossibleFiguresTrue(m: Int, n: Int, lis: List[Cell]): List[Cell] = ???

  override def setPosiesFalse(n: Int): gameBoardBaseImpl.GameBoard = ???

  override def setPossibilitiesFalse(n: Int): Cell = ???

  override def setPossibleFiguresFalse(m: Int, n: Int, lis: List[Cell]): List[Cell] = ???

  override def execute(callback: Int => gameBoardBaseImpl.GameBoard, y: Int): gameBoardBaseImpl.GameBoard = ???

  override def nextPlayer(list: List[Player], n: Int): Player = ???

  override def createPlayer(text: String): gameBoardBaseImpl.GameBoard = ???

  override def createGameBoard(): String = ???

  override def getCellList: List[Cell] = ???

  override def getPlayer: List[Player] = ???

  override def getGameBoardGraph: mutable.Map[Int, Set[Int]] = ???

  override def getPossibleCells: Set[Int] = ???
}
