package de.htwg.se.malefiz.model

import java.awt.Color

import scala.collection.mutable
import scala.collection.mutable.{Map, Queue}
import scala.io.Source
import scala.io.StdIn.readLine

case class GameBoard(cellList: List[Cell], players: List[Player],
                     gameBoardGraph: Map[Int, Set[Int]], possibleCells: Set[Int] = Set().empty) {

/*
  def fOnCell(fN: Int, cN: Int) : Cell  = {
    cellList(cN).copy(figureNumber = fN)
  }

  def pOnCell(pN: Int, cN: Int) : Cell  = {
    cellList(cN).copy(playerNumber = pN)
  }

  def setPlayer(pN: Int, s: String) : List[Cell] = {

    var Farbe = Color.GRAY
    //var benutzeFarbe : Set[Color] = Set().empty

    s match {
      case "blue" => Farbe = Color.BLUE
      case "red" => Farbe = Color.RED
      case "green" => Farbe = Color.GREEN
      case "yellow" => Farbe = Color.YELLOW
    }

    pN match {
      case 1 => Farbe match {
        case Color.BLUE =>
          var zahl = 1
          for (i <- 15 to 19) {
            pOnCell(pN, i)
            fOnCell(zahl, i)
            zahl = zahl + 1
          }
        case Color.YELLOW =>
          var zahl = 1
          for (i <- 10 to 14) {
            pOnCell(pN, i)
            fOnCell(zahl, i)
            zahl = zahl + 1
          }
        case Color.GREEN =>
          var zahl = 1
          for (i <- 5 to 9) {
            pOnCell(pN, i)
            fOnCell(zahl, i)
            zahl = zahl + 1
          }
        case Color.RED =>
          var zahl = 1
          for (i <- 5 to 9) {
            pOnCell(pN, i)
            fOnCell(zahl, i)
            zahl = zahl + 1
          }
      }
      case 2 => Farbe match {
        case Color.BLUE =>
          var zahl = 1
          for (i <- 15 to 19) {
            pOnCell(pN, i)
            fOnCell(zahl, i)
            zahl = zahl + 1
          }
        case Color.YELLOW =>
          var zahl = 1
          for (i <- 10 to 14) {
            pOnCell(pN, i)
            fOnCell(zahl, i)
            zahl = zahl + 1
          }
        case Color.GREEN =>
          var zahl = 1
          for (i <- 5 to 9) {
            pOnCell(pN, i)
            fOnCell(zahl, i)
            zahl = zahl + 1
          }
        case Color.RED =>
          var zahl = 1
          for (i <- 5 to 9) {
            pOnCell(pN, i)
            fOnCell(zahl, i)
            zahl = zahl + 1
          }
      }
      case 3 => Farbe match {
        case Color.BLUE =>
          var zahl = 1
          for (i <- 15 to 19) {
            pOnCell(pN, i)
            fOnCell(zahl, i)
            zahl = zahl + 1
          }
        case Color.YELLOW =>
          var zahl = 1
          for (i <- 10 to 14) {
            pOnCell(pN, i)
            fOnCell(zahl, i)
            zahl = zahl + 1
          }
        case Color.GREEN =>
          var zahl = 1
          for (i <- 5 to 9) {
            pOnCell(pN, i)
            fOnCell(zahl, i)
            zahl = zahl + 1
          }
        case Color.RED =>
          var zahl = 1
          for (i <- 5 to 9) {
            pOnCell(pN, i)
            fOnCell(zahl, i)
            zahl = zahl + 1
          }
      }
      case 4 => Farbe match {
        case Color.BLUE =>
          var zahl = 1
          for (i <- 15 to 19) {
            pOnCell(pN, i)
            fOnCell(zahl, i)
            zahl = zahl + 1
          }
        case Color.YELLOW =>
          var zahl = 1
          for (i <- 10 to 14) {
            pOnCell(pN, i)
            fOnCell(zahl, i)
            zahl = zahl + 1
          }
        case Color.GREEN =>
          var zahl = 1
          for (i <- 5 to 9) {
            pOnCell(pN, i)
            fOnCell(zahl, i)
            zahl = zahl + 1
          }
        case Color.RED =>
          var zahl = 1
          for (i <- 5 to 9) {
            pOnCell(pN, i)
            fOnCell(zahl, i)
            zahl = zahl + 1
          }
      }
    }
    cellList
  }
*/
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
  }

  def setPlayerOnCell(pN: Int, cN : Int) : Cell = {
    cellList(cN).copy(playerNumber = pN)
  }

  def removeActualPlayerAndFigureFromCell(pN: Int, fN: Int, cN: Int): GameBoard = {
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

  def getPlayerFigure(pN: Int, fN: Int) : Int = {
    val feldNumber = cellList.filter(cell => cell.playerNumber == pN && cell.figureNumber == fN)
    val feld = feldNumber.head.cellNumber
    feld
  }

  def getPossibleCells(start: Int, cube: Int): GameBoard = {
    var found: Set[Int] = Set[Int]()
    var needed: Set[Int] = Set[Int]()

    def recurse(current: Int, times: Int): Unit = {
      if (times == 0) {
        needed += current
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

  def createGameBoard(): String = buildString(cellList)

}