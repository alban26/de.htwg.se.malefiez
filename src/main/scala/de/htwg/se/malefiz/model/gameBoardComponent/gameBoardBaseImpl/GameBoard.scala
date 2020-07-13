package de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl

import de.htwg.se.malefiz.model.gameBoardComponent.GameBoardInterface
import de.htwg.se.malefiz.model.playerComponent.Player
import scala.collection.mutable.Map
import de.htwg.se.malefiz.Malefiz._

case class GameBoard (cellList: List[Cell],
                      players: List[Player],
                      gameBoardGraph: Map[Int, Set[Int]],
                      possibleCells: Set[Int] = Set.empty) extends GameBoardInterface {

  def this() = this(Creator().getCellList(cellConfigFile),List.empty,Creator().getCellGraph(cellLinksFile),Set.empty)

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

  override def setPlayerFigureOnCell(fN: Int, cN: Int) : Cell  = cellList(cN).copy(figureNumber = fN)

  override def setPlayerOnCell(pN: Int, cN : Int) : Cell = cellList(cN).copy(playerNumber = pN)

  override def removeActualPlayerAndFigureFromCell(pN: Int, fN: Int): GameBoard = {

    val a = getPlayerFigure(pN, fN)
    copy(cellList.updated(a, removePlayerFigureOnCell(a)))
    copy(cellList.updated(a, removePlayerOnCell(a)))

  }

  override def setFigure(fN: Int, cN: Int): GameBoard = copy(cellList.updated(cN, setPlayerFigureOnCell(fN, cN)))

  override def setPlayer(pN: Int, cN: Int): GameBoard = copy(cellList.updated(cN, setPlayerOnCell(pN, cN)))

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

  override def getPossibleCells(start: Int, cube: Int): GameBoard = {

    var found: Set[Int] = Set[Int]()
    var needed: Set[Int] = Set[Int]()

    def recurse(current: Int, times: Int): Unit = {

      if (times == 0 || cellList(current).hasWall && times == 0) {
        needed += current
      }
      if (times != 0 && cellList(current).hasWall) {
        return
      }
      found += current
      for (next <- gameBoardGraph(current)) {
        if (!found.contains(next) && times != 0 ) {
          recurse(next, times-1)
        }
      }
    }
    recurse(start, cube)
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

  override def setPossibleFiguresTrue(m: Int, n: Int, lis: List[Cell]): List[Cell] = {

      if (m == -1) {
        lis
      } else if (lis(m).playerNumber == n) {
        setPossibleFiguresTrue(m-1,n,lis.updated(lis(m).cellNumber,setPossibilitiesTrue(m)))
      }
      else {
        setPossibleFiguresTrue(m-1,n,lis)
      }

  }

  override def setPossibilitiesTrue(cellNumber: Int): Cell = cellList(cellNumber).copy(possibleFigures = true)


  override def setPosiesFalse(cellNumber: Int): GameBoard = copy(setPossibleFiguresFalse(cellList.length-1, cellNumber, cellList))

  override def setPossibilitiesFalse(cellNumber: Int): Cell = cellList(cellNumber).copy(possibleFigures = false)

  override def setPossibleFiguresFalse(m: Int, n: Int, lis: List[Cell]): List[Cell] = {
    if (m == -1) {
      lis
    } else if (lis(m).playerNumber == n) {
      setPossibleFiguresFalse(m-1,n,lis.updated(lis(m).cellNumber,setPossibilitiesFalse(m)))
    }
    else {
      setPossibleFiguresFalse(m-1,n,lis)
    }
  }

  override def execute(callback: Int => GameBoard, y:Int): GameBoard = callback(y)

  override def nextPlayer(list: List[Player], n: Int): Player = {

    if(n == list.length-1) {
      list.head
    } else {
      list(n+1)
    }

  }

  override def createPlayer(text: String): GameBoard = copy(players =  players :+ Player(players.length+1, text))

  override def createGameBoard(): String = buildString(cellList)

  override def getCellList: List[Cell] = this.cellList

  override def getPlayer: List[Player] = this.players

  override def getPossibleCells: Set[Int] = this.possibleCells

  override def setPossibleCell(pC: Set[Int]) : GameBoard = copy(possibleCells = pC)

  override def clearPossibleCells: GameBoard = copy(possibleCells = Set.empty)
}
