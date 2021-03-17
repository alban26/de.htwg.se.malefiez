package de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl

import de.htwg.se.malefiz.model.gameBoardComponent.GameBoardInterface
import de.htwg.se.malefiz.model.playerComponent.Player
import scala.collection.mutable.Map
import de.htwg.se.malefiz.Malefiz._

case class GameBoard (cellList: List[Cell],
                      players: List[Player],
                      gameBoardGraph: Map[Int, Set[Int]],
                      possibleCells: Set[Int] = Set.empty) extends GameBoardInterface {

  def this() = this(Creator().getCellList(cellConfigFile), List.empty, Creator().getCellGraph(cellLinksFile), Set.empty)

  override def s(n: Int): Int = n * 4 + 1

  override def buildPlayerString(list: List[Cell]): String = {

    val abstand1 = ""
    val abstand = "    "
    val l = list.slice(0,20)
    s"""|$abstand${l.mkString(s"${abstand1}")}
        |""".stripMargin

  }

  override def buildString(list: List[Cell]): String = createStringValues(list, 20, 0, 6, 0, 4) + buildPlayerString(list)

  override def createStringValues(list: List[Cell],n: Int,O: Int,z: Int,i: Int,l: Int): String = {

    val abstand = " "
    val strecke = s(l)
    val gapBetween = abstand * 13

    if(i == z) {
      val sliceBeginU = O
      val sliceEndU = sliceBeginU + strecke
      val sliceBeginO = sliceEndU
      val sliceEndO = sliceBeginO + l - 3
      val gapLeftU = abstand * 0
      val gapLeftO = abstand*32
      val gapBetween = abstand*20
      createString(list, n, sliceBeginU, sliceEndU, sliceBeginO, sliceEndO, gapLeftO,gapLeftU, gapBetween, z, i,l)
    } else if(i == 1) {
      val sliceBeginU = O
      val sliceEndU = sliceBeginU + strecke
      val sliceBeginO = sliceEndU
      val sliceEndO = sliceBeginO + l
      val gapLeftU = abstand * 0
      val gapLeftO = abstand * 8
      createString(list, n, sliceBeginU, sliceEndU, sliceBeginO, sliceEndO, gapLeftO,gapLeftU, gapBetween, z, i,l)
    }
    else if(i == 2){
      val sliceBeginU = O
      val sliceEndU = sliceBeginU + strecke-4
      val sliceBeginO = sliceEndU
      val sliceEndO = sliceBeginO + l-2
      val gapLeftU = abstand * 8
      val gapLeftO = abstand * 16
      val gapBetween = abstand * 29
      createString(list, n, sliceBeginU, sliceEndU, sliceBeginO, sliceEndO, gapLeftO,gapLeftU, gapBetween, z, i,l)
    } else if(i == 3) {
      val sliceBeginU = O
      val sliceEndU = sliceBeginU + strecke-8
      val sliceBeginO = sliceEndU
      val sliceEndO = sliceBeginO + l-2
      val gapLeftU = abstand * 16
      val gapLeftO = abstand * 24
      val gapBetween = abstand * 13
      createString(list, n, sliceBeginU, sliceEndU, sliceBeginO, sliceEndO, gapLeftO,gapLeftU, gapBetween, z, i,l)
    } else if(i == 4) {
      val sliceBeginU = O
      val sliceEndU = sliceBeginU + strecke-12
      val sliceBeginO = sliceEndU
      val sliceEndO = sliceBeginO + l-3
      val gapLeftU = abstand * 24
      val gapLeftO = abstand * 32
      val gapBetween = abstand * 13
      createString(list, n, sliceBeginU, sliceEndU, sliceBeginO, sliceEndO, gapLeftO,gapLeftU, gapBetween, z, i,l)
    }
    else if(i == 5) {
      val sliceBeginU = O
      val sliceEndU = sliceBeginU + s(l)
      val sliceBeginO = sliceEndU
      val sliceEndO = sliceBeginO + l - 2
      val gapLeftU = abstand * 0
      val gapLeftO = abstand * 0
      val gapBetween = abstand * 61
      createString(list, n, sliceBeginU, sliceEndU, sliceBeginO, sliceEndO, gapLeftO,gapLeftU, gapBetween, z, i,l)
    }
    else {
      val gapLeftO = abstand * 0
      val gapLeftU = abstand * 0
      val sliceBeginU = n
      val sliceEndU = sliceBeginU + strecke
      val sliceBeginO = sliceEndU
      val sliceEndO = sliceBeginO + l + 1
      createString(list, n, sliceBeginU, sliceEndU, sliceBeginO, sliceEndO, gapLeftO,gapLeftU, gapBetween, z, i,l)
    }

  }

  override def createString(list: List[Cell],n: Int,sliceBeginU: Int, sliceEndU: Int,sliceBeginO: Int,
                   sliceEndO: Int,gapLeftO: String,gapLeftU: String, gapBetween: String, z: Int,i: Int,l: Int): String = {

    val ol = list.slice(sliceBeginO,sliceEndO)
    val ul = list.slice(sliceBeginU,sliceEndU)
    val ol1 = for(t <- ol)yield t
    val ul1 = for(t <- ul)yield t
    if(i == z) {
      s"""|${gapLeftO}${ol1.mkString(s"${gapBetween}")}
          |${gapLeftU}${ul1.mkString("-")}
          |""".stripMargin
    } else {
      createStringValues(list,n,sliceEndO,z,i+1,l) +  s"""|${gapLeftO}${ol1.mkString(s"${gapBetween}")}
                                                        |${gapLeftU}${ul1.mkString("-")}
                                                        |""".stripMargin
    }

  }

  override def removePlayerFigureOnCell(cellNumber: Int) :Cell  = cellList(cellNumber).copy(figureNumber = 0)

  override def removePlayerOnCell(cellNumber : Int) : Cell = cellList(cellNumber).copy(playerNumber = 0)

  override def setPlayerFigureOnCell(figureNumber: Int, cellNumber: Int) : Cell  = cellList(cellNumber).copy(figureNumber = figureNumber)

  override def setPlayerOnCell(playerNumber: Int, cellNumber : Int) : Cell = cellList(cellNumber).copy(playerNumber = playerNumber)

  override def removeActualPlayerAndFigureFromCell(playerNumber: Int, figureNumber: Int): GameBoard = {
    val cell = getPlayerFigure(playerNumber, figureNumber)
    copy(cellList.updated(cell, removePlayerFigureOnCell(cell)))
    copy(cellList.updated(cell, removePlayerOnCell(cell)))
  }

  override def setFigure(figureNumber: Int, cellNumber: Int): GameBoard =
    copy(cellList.updated(cellNumber, setPlayerFigureOnCell(figureNumber, cellNumber)))

  override def setPlayer(playerNumber: Int, cellNumber: Int): GameBoard =
    copy(cellList.updated(cellNumber, setPlayerOnCell(playerNumber, cellNumber)))

  override def getHomeNr(pN: Int, fN: Int): Int = {
    if(pN == 1 && fN == 1) {
      0
    }else {
      (pN - 1) * 5 + fN - 1
    }
  }

  override def getPlayerFigure(pN: Int, fN: Int) : Int = {
    val feldNumber = cellList.filter(cell => cell.playerNumber == pN && cell.figureNumber == fN)
    val feld = feldNumber.head.cellNumber
    feld
  }

  override def getPossibleCells(startCell: Int, diceNumber: Int): GameBoard = {

    var found: Set[Int] = Set[Int]()
    var needed: Set[Int] = Set[Int]()

    def recurse(currentCell: Int, diceNumber: Int): Unit = {
      if (diceNumber == 0 || cellList(currentCell).hasWall && diceNumber == 0) {
        needed += currentCell
      }
      if (diceNumber != 0 && cellList(currentCell).hasWall) {
        return
      }
      found += currentCell
      gameBoardGraph(currentCell).foreach(x => if (!found.contains(x) && diceNumber != 0) recurse(x, diceNumber-1))
    }
    recurse(startCell, diceNumber)
    copy(possibleCells = needed)

  }

  override def removeWall(cellNumber: Int): GameBoard = copy(removeListWall(cellNumber))

  override def removeListWall(cellNumber: Int): List[Cell] = cellList.updated(cellNumber, setHasWallFalse(cellNumber))

  override def setHasWallFalse(cellNumber: Int): Cell = cellList(cellNumber).copy(hasWall = false)

  override def setWall(cellNumber: Int): GameBoard = copy(updateListWall(cellNumber))

  override def updateListWall(cellNumber: Int): List[Cell] =  {

    if (cellNumber >= 42 && !cellList(cellNumber).hasWall) {
     cellList.updated(cellNumber, placeWall(cellNumber))
    }
    else {
      cellList
    }

  }

  override def placeWall(cellNumber: Int): Cell = cellList(cellNumber).copy(hasWall = true)

  override def setPosiesCellTrue(n: List[Int]): GameBoard = copy(setPossibleCell1True(cellList.length-1, n, cellList))

  override def setPossibleCell1True(m: Int, n: List[Int], lis: List[Cell]): List[Cell] = {

    if (m == -1) {
      lis
    } else if (n contains lis(m).cellNumber) {
      setPossibleCell1True(m-1,n,lis.updated(lis(m).cellNumber,setPossibleCellTrue(m)))
    }
    else {
      setPossibleCell1True(m-1,n,lis)
    }

  }

  override def setPossibleCellTrue(cellNumber: Int): Cell = cellList(cellNumber).copy(possibleCells = true)


  override def setPosiesCellFalse(n: List[Int]): GameBoard = copy(setPossibleCell1False(cellList.length-1, n, cellList))

  override def setPossibleCell1False(m: Int, n: List[Int], lis: List[Cell]): List[Cell] = {

    if (m == -1) {
      lis
    } else if (n contains lis(m).cellNumber) {
      setPossibleCell1False(m - 1, n, lis.updated(lis(m).cellNumber, setPossibleCellFalse(m)))
    }
    else {
      setPossibleCell1False(m-1,n,lis)
    }

  }

  override def setPossibleCellFalse(cellNumber: Int): Cell = cellList(cellNumber).copy(possibleCells = false)

  override def setPosiesTrue(cellNumber: Int): GameBoard = copy(setPossibleFiguresTrue(cellList.length-1, cellNumber, cellList))

  override def setPossibleFiguresTrue(cellListLength: Int, cellNumber: Int, cellList: List[Cell]): List[Cell] = {

      if (cellListLength == -1) {
        cellList
      } else if (cellList(cellListLength).playerNumber == cellNumber) {
        setPossibleFiguresTrue(cellListLength-1,
            cellNumber,
            cellList.updated(cellList(cellListLength).cellNumber,
            setPossibilitiesTrue(cellListLength)))
      }
      else {
        setPossibleFiguresTrue(cellListLength-1,cellNumber,cellList)
      }

  }

  override def setPossibilitiesTrue(cellNumber: Int): Cell = cellList(cellNumber).copy(possibleFigures = true)


  override def setPosiesFalse(cellNumber: Int): GameBoard = copy(setPossibleFiguresFalse(cellList.length-1, cellNumber, cellList))

  override def setPossibilitiesFalse(cellNumber: Int): Cell = cellList(cellNumber).copy(possibleFigures = false)

  override def setPossibleFiguresFalse(m: Int, n: Int, cellList: List[Cell]): List[Cell] = {
    if (m == -1) {
      cellList
    } else if (cellList(m).playerNumber == n) {
      setPossibleFiguresFalse(m-1,n,cellList.updated(cellList(m).cellNumber,setPossibilitiesFalse(m)))
    }
    else {
      setPossibleFiguresFalse(m-1,n,cellList)
    }
  }

  override def execute(callback: Int => GameBoard, y: Int): GameBoard = callback(y)

  override def nextPlayer(playerList: List[Player], playerNumber: Int): Player = {
    if(playerNumber == playerList.length-1) {
      playerList.head
    } else {
      playerList(playerNumber+1)
    }
  }

  override def createPlayer(text: String): GameBoard = copy(players =  players :+ Player(players.length+1, text))

  override def createGameBoard(): String = buildString(cellList)

  override def getCellList: List[Cell] = this.cellList

  override def getPlayer: List[Player] = this.players

  override def getPossibleCells: Set[Int] = this.possibleCells

  override def setPossibleCell(possibleCells: Set[Int]) : GameBoard = copy(possibleCells = possibleCells)

  override def clearPossibleCells: GameBoard = copy(possibleCells = Set.empty)
}
