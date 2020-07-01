package de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl

import de.htwg.se.malefiz.model.gameBoardComponent.GameboardInterface
import de.htwg.se.malefiz.model.playerComponent.Player

import scala.collection.mutable.Map

case class GameBoard(cellList: List[Cell], players: List[Player],
                     gameBoardGraph: Map[Int, Set[Int]], possibleCells: Set[Int] = Set().empty) extends GameboardInterface {



  def s(n: Int): Int = n * 4 + 1

  def buildPlayerString(list: List[Cell]): String = {
    val abstand1 = ""
    val abstand = "    "
    val l = list.slice(0,20)
    s"""|$abstand${l.mkString(s"${abstand1}")}
        |""".stripMargin
  }

  def buildString(list: List[Cell]): String = {
    createStringValues(list,20, 0,6,0,4) + buildPlayerString(list)
  }

  def createStringValues(list: List[Cell],n: Int,O: Int,z: Int,i: Int,l: Int): String = {
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

  def createString(list: List[Cell],n: Int,sliceBeginU: Int, sliceEndU: Int,sliceBeginO: Int,
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

  def removePlayerFigureOnCell(cN: Int) :Cell  = {
    cellList(cN).copy(figureNumber = 0)
  }

  def removePlayerOnCell(n : Int) : Cell = {
    cellList(n).copy(playerNumber = 0)
  }

  def setPlayerFigureOnCell(fN: Int, cN: Int) : Cell  = {
    cellList(cN).copy(figureNumber = fN)
    //cellList(cN).copy(hasWall = false)
  }

  def setPlayerOnCell(pN: Int, cN : Int) : Cell = {
    cellList(cN).copy(playerNumber = pN)
  }

  def removeActualPlayerAndFigureFromCell(pN: Int, fN: Int): GameBoard = {
    val a = getPlayerFigure(pN, fN)

    copy(cellList.updated(a, removePlayerFigureOnCell(a)))
    copy(cellList.updated(a, removePlayerOnCell(a)))
  }

  def setFigure(fN: Int, cN: Int): GameBoard = {
    copy(cellList.updated(cN, setPlayerFigureOnCell(fN, cN)))
  }

  def setPlayer(pN: Int, cN: Int): GameBoard = {
    copy(cellList.updated(cN, setPlayerOnCell(pN, cN)))
  }

  def getHomeNr(pN: Int, fN: Int): Int = {
    if(pN == 1 && fN == 1) {
      0
    }else {
      (pN - 1) * 5 + fN - 1
    }
  }

  def getPlayerFigure(pN: Int, fN: Int) : Int = {
    val feldNumber = cellList.filter(cell => cell.playerNumber == pN && cell.figureNumber == fN)
    val feld = feldNumber.head.cellNumber
    feld
  }


  def getPossibleCells(start: Int, cube: Int): GameBoard = {
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


  def removeWall(n: Int): Cell = cellList(n).copy(hasWall = false)

  def rWall(n: Int): GameBoard = copy(removeListWall(n))

  def removeListWall(n: Int): List[Cell] =  {
      cellList.updated(n, removeWall(n))
  }


  def placeWall(n: Int): Cell = cellList(n).copy(hasWall = true)

  def setWall(n: Int): GameBoard = copy(updateListWall(n))

  def updateListWall(n: Int): List[Cell] =  {
    if (n >= 42 && !cellList(n).hasWall) {
     cellList.updated(n, placeWall(n))
    }
    else {
      cellList
    }
  }




  def setPosiesCellTrue(n: List[Int]): GameBoard = copy(setPossibleCell1True(cellList.length-1, n, cellList))

  def setPossibleCellTrue(n: Int): Cell = cellList(n).copy(possibleCells = true)

  def setPossibleCell1True(m: Int, n: List[Int], lis: List[Cell]): List[Cell] = {
    if (m == -1) {
      lis
    } else if (n contains lis(m).cellNumber) {
      setPossibleCell1True(m-1,n,lis.updated(lis(m).cellNumber,setPossibleCellTrue(m)))
    }
    else {
      setPossibleCell1True(m-1,n,lis)
    }
  }


  def setPosiesCellFalse(n: List[Int]): GameBoard = copy(setPossibleCell1False(cellList.length-1, n, cellList))

  def setPossibleCellFalse(n: Int): Cell = cellList(n).copy(possibleCells = false)

  def setPossibleCell1False(m: Int, n: List[Int], lis: List[Cell]): List[Cell] = {
    if (m == -1) {
      lis
    } else if (n contains lis(m).cellNumber) {
      setPossibleCell1False(m - 1, n, lis.updated(lis(m).cellNumber, setPossibleCellFalse(m)))
    }
    else {
      setPossibleCell1False(m-1,n,lis)
    }
  }

  def setPosiesTrue(n: Int): GameBoard = copy(setPossibleFiguresTrue(cellList.length-1, n, cellList))

  def setPossibilitiesTrue(n: Int): Cell = cellList(n).copy(possibleFigures = true)

  def setPossibleFiguresTrue(m: Int, n: Int, lis: List[Cell]): List[Cell] = {
      if (m == -1) {
        lis
      } else if (lis(m).playerNumber == n) {
        setPossibleFiguresTrue(m-1,n,lis.updated(lis(m).cellNumber,setPossibilitiesTrue(m)))
      }
      else {
        setPossibleFiguresTrue(m-1,n,lis)
      }
  }


  def setPosiesFalse(n: Int): GameBoard = copy(setPossibleFiguresFalse(cellList.length-1, n, cellList))

  def setPossibilitiesFalse(n: Int): Cell = cellList(n).copy(possibleFigures = false)

  def setPossibleFiguresFalse(m: Int, n: Int, lis: List[Cell]): List[Cell] = {
    if (m == -1) {
      lis
    } else if (lis(m).playerNumber == n) {
      setPossibleFiguresFalse(m-1,n,lis.updated(lis(m).cellNumber,setPossibilitiesFalse(m)))
    }
    else {
      setPossibleFiguresFalse(m-1,n,lis)
    }
  }

  def execute(callback:(Int) => GameBoard, y:Int) = callback(y)

  def nextPlayer(list: List[Player], n: Int): Player = {
    if(n == list.length-1) {
      list.head
    } else {
      list(n+1)
    }
  }

  def createPlayer(text: String): GameBoard = {
      "Spieler " + text + " wurde erzeugt - bitte trage noch mindestens einen weiteren Spieler ein."
      copy(players =  players :+ Player(players.length+1, text))
  }



  def createGameBoard(): String = buildString(cellList)

}
