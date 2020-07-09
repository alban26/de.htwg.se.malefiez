package de.htwg.se.malefiz.model.gameBoardComponent

import de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl.{Cell, GameBoard}
import de.htwg.se.malefiz.model.playerComponent.Player
import scala.collection.mutable.Map
import scala.util.Try

trait GameboardInterface {

  def clearPossibleCells: GameBoard

  def getCellList: List[Cell]

  def getPlayer: List[Player]

  def getGameBoardGraph: Map[Int, Set[Int]]

  def getPossibleCells: Set[Int]

  def s(n: Int): Int

  def buildPlayerString(list: List[Cell]): String

  def buildString(list: List[Cell]): String

  def createStringValues(list: List[Cell],n: Int,O: Int,z: Int,i: Int,l: Int): String

  def createString(list: List[Cell],n: Int,sliceBeginU: Int, sliceEndU: Int,sliceBeginO: Int,
                   sliceEndO: Int,gapLeftO: String,gapLeftU: String, gapBetween: String, z: Int,i: Int,l: Int): String

  def removePlayerFigureOnCell(cN: Int) :Cell

  def removePlayerOnCell(n : Int) : Cell

  def setPlayerFigureOnCell(fN: Int, cN: Int) : Cell

  def setPlayerOnCell(pN: Int, cN : Int) : Cell

  def removeActualPlayerAndFigureFromCell(pN: Int, fN: Int): GameBoard

  def setFigure(fN: Int, cN: Int): GameBoard

  def setPlayer(pN: Int, cN: Int): GameBoard

  def getHomeNr(pN: Int, fN: Int): Int

  def getPlayerFigure(pN: Int, fN: Int) : Int

  def getPossibleCells(start: Int, cube: Int): GameBoard

  def removeWall(n: Int): Cell

  def rWall(n: Int): GameBoard

  def removeListWall(n: Int): List[Cell]

  def placeWall(n: Int): Cell

  def setWall(n: Int): GameBoard

  def updateListWall(n: Int): List[Cell]

  def setPosiesCellTrue(n: List[Int]): GameBoard

  def setPossibleCellTrue(n: Int): Cell

  def setPossibleCell1True(m: Int, n: List[Int], lis: List[Cell]): List[Cell]

  def setPosiesCellFalse(n: List[Int]): GameBoard

  def setPossibleCellFalse(n: Int): Cell

  def setPossibleCell1False(m: Int, n: List[Int], lis: List[Cell]): List[Cell]

  def setPosiesTrue(n: Int): GameBoard

  def setPossibilitiesTrue(n: Int): Cell

  def setPossibleFiguresTrue(m: Int, n: Int, lis: List[Cell]): List[Cell]

  def setPosiesFalse(n: Int): GameBoard

  def setPossibilitiesFalse(n: Int): Cell

  def setPossibleFiguresFalse(m: Int, n: Int, lis: List[Cell]): List[Cell]

  def execute(callback: Int => GameBoard, y:Int): GameBoard

  def nextPlayer(list: List[Player], n: Int): Player

  def createPlayer(text: String): GameBoard

  def createGameBoard(): String

  def setPossibleCell(pC: Set[Int]) : GameBoard
}

trait CubeInterface {

  def getRandomNumber : Int

}

trait CreatorInterface {

  def readTextFile(filename: String): Try[Iterator[String]]

  def getCellList(inputFile: String): List[Cell]

  def getCellGraph(fileInput: String): Map[Int, Set[Int]]

  def updateCellGraph(key: Int, value: Int, map: Map[Int, Set[Int]]) : Map[Int, Set[Int]]

  def execute(callback:(String) => List[Cell], y: String): List[Cell]

  def execute1(callback: String => Map[Int, Set[Int]],y: String): Map[Int, Set[Int]]

}