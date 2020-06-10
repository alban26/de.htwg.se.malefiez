package de.htwg.se.malefiz.model

import scala.collection.mutable.{Map, Queue}
import scala.io.Source
import scala.io.StdIn.readLine

case class GameBoard(cellList: List[Cell], players: List[Player], gameBoardGraph: Map[Int, Set[Int]]) {

  def s(n: Int): Int = n * 4 + 1

  def buildPlayerString(list: List[Cell]): String = {
    val abstand1 = "             "
    val abstand = "        "
    val l = list.slice(0,4)
    s"""|$abstand${l.mkString(s"${abstand1}")}
        |""".stripMargin
  }

  def buildString(list: List[Cell]): String = {
    createStringValues(list,4, 0,6,0) + buildPlayerString(list)
  }

  def createStringValues(list: List[Cell],n: Int,O: Int,z: Int,i: Int): String = {
    val abstand = " "
    val strecke = s(n)
    val gapBetween = abstand * 13

    if(i == z) {
      val sliceBeginU = O
      val sliceEndU = sliceBeginU + strecke
      val sliceBeginO = sliceEndU
      val sliceEndO = sliceBeginO + n - 3
      val gapLeftU = abstand * 0
      val gapLeftO = abstand*32
      val gapBetween = abstand*20
      createString(list, n, sliceBeginU, sliceEndU, sliceBeginO, sliceEndO, gapLeftO,gapLeftU, gapBetween, z, i)

    } else if(i == 1) {

      val sliceBeginU = O
      val sliceEndU = sliceBeginU + strecke
      val sliceBeginO = sliceEndU
      val sliceEndO = sliceBeginO + n
      val gapLeftU = abstand * 0
      val gapLeftO = abstand * 8
      createString(list, n, sliceBeginU, sliceEndU, sliceBeginO, sliceEndO, gapLeftO,gapLeftU, gapBetween, z, i)
    }
    else if(i == 2){
      val sliceBeginU = O
      val sliceEndU = sliceBeginU + strecke-4
      val sliceBeginO = sliceEndU
      val sliceEndO = sliceBeginO + n-2
      val gapLeftU = abstand * 8
      val gapLeftO = abstand * 16
      val gapBetween = abstand * 29
      createString(list, n, sliceBeginU, sliceEndU, sliceBeginO, sliceEndO, gapLeftO,gapLeftU, gapBetween, z, i)
    } else if(i == 3) {
      val sliceBeginU = O
      val sliceEndU = sliceBeginU + strecke-8
      val sliceBeginO = sliceEndU
      val sliceEndO = sliceBeginO + n-2
      val gapLeftU = abstand * 16
      val gapLeftO = abstand * 24
      val gapBetween = abstand * 13
      createString(list, n, sliceBeginU, sliceEndU, sliceBeginO, sliceEndO, gapLeftO,gapLeftU, gapBetween, z, i)
    } else if(i == 4) {
      val sliceBeginU = O
      val sliceEndU = sliceBeginU + strecke-12
      val sliceBeginO = sliceEndU
      val sliceEndO = sliceBeginO + n-3
      val gapLeftU = abstand * 24
      val gapLeftO = abstand * 32
      val gapBetween = abstand * 13
      createString(list, n, sliceBeginU, sliceEndU, sliceBeginO, sliceEndO, gapLeftO,gapLeftU, gapBetween, z, i)
    }
    else if(i == 5) {
      val sliceBeginU = O
      val sliceEndU = sliceBeginU + s(n)
      val sliceBeginO = sliceEndU
      val sliceEndO = sliceBeginO + n - 2
      val gapLeftU = abstand * 0
      val gapLeftO = abstand * 0
      val gapBetween = abstand * 61
      createString(list, n, sliceBeginU, sliceEndU, sliceBeginO, sliceEndO, gapLeftO,gapLeftU, gapBetween, z, i)
    }
    else {
      val gapLeftO = abstand * 0
      val gapLeftU = abstand * 0
      val sliceBeginU = n
      val sliceEndU = sliceBeginU + strecke
      val sliceBeginO = sliceEndU
      val sliceEndO = sliceBeginO + n + 1
      createString(list, n, sliceBeginU, sliceEndU, sliceBeginO, sliceEndO, gapLeftO,gapLeftU, gapBetween, z, i)
    }
  }

  def createString(list: List[Cell],n: Int,sliceBeginU: Int, sliceEndU: Int,sliceBeginO: Int,
                   sliceEndO: Int,gapLeftO: String,gapLeftU: String, gapBetween: String, z: Int,i: Int): String = {
    val ol = list.slice(sliceBeginO,sliceEndO)
    val ul = list.slice(sliceBeginU,sliceEndU)
    val ol1 = for(t <- ol)yield t
    val ul1 = for(t <- ul)yield t
    if(i == z) {
      s"""|${gapLeftO}${ol1.mkString(s"${gapBetween}")}
          |${gapLeftU}${ul1.mkString("-")}
          |""".stripMargin
    } else {
      createStringValues(list,n,sliceEndO,z,i+1) +  s"""|${gapLeftO}${ol1.mkString(s"${gapBetween}")}
                                                        |${gapLeftU}${ul1.mkString("-")}
                                                        |""".stripMargin
    }
  }

  def createPlayerArray(numberOfPlayers: Int) : GameBoard = {

    var newPlayerList : List[Player] = List()
    for (i <- 0 until numberOfPlayers) {
      println("Spieler " + (i+1) + " geben Sie ihren Namen ein: ")
      val name = readLine()
      println("Spieler " + (i+1) + " geben Sie ihre Farbe ein: ")
      val colour = readLine()
      newPlayerList = newPlayerList :+ Player(i, name, colour)
      println(newPlayerList(i).toString)
    }
    copy(players = newPlayerList)
  }













/*
  def changePlayerFigure(playerNumber: Int, figureNumber: Int, cell: Int): List[Player] = {
    val a = getNewArrayFigure(playerNumber, figureNumber, cell)
    val b = setFigure(playerNumber, figureNumber, cell)
    copy(players = players.updated(playerNumber, players(playerNumber).playerFigures(figureNumber) = b))
  }

*/
  //def setPlayer(pN: Int, fN: Int, cN: Int): GameBoard = copy(players = playerList(pN,fN,cN))

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
    copy(cellList.updated(a, removePlayerOnCell(a)))
    copy(cellList.updated(a, removePlayerFigureOnCell(a)))

  }
/*
  def removeActualFigureFromCell(pN: Int, fN: Int): GameBoard = {
    copy(cellList.updated(getPlayerFigure(pN, fN), removePlayerFigureOnCell(getPlayerFigure(pN, fN))))
  }
*/
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

  val menge : Set[Int] = Set()

/*
  def getPossibleCells(fN: Int,  cube: Int): Set[Int] = {
     var besucht : Set[Int] = Set(fN)
     if (besucht.contains(fN))
       return


*/

  def getPossibleCells(start: Int, cube: Int): Set[Int] = {
    var found: Set[Int] = Set[Int]()
    var needed: Set[Int] = Set[Int]()

    def recurse(current: Int, times: Int): Unit = {
      if (times == 0) {
        needed += current
      }
      found += current
      for (next <- gameBoardGraph(current)) {
        print(next+ " ")
        if (!found.contains(next) && times != 0 ) {
          recurse(next, times-1)
        }
      }
    }
    recurse(start, cube)
    needed
  }


  def removeWall(n: Int): Cell = cellList(n).copy(hasWall = true)

  def rWall(n: Int): GameBoard = copy(removeListWall(n))

  def removeListWall(n: Int): List[Cell] =  {

      cellList.updated(n, removeWall(n))
  }



  def placeWall(n: Int): Cell = cellList(n).copy(hasWall = true)

  def setWall(n: Int): GameBoard = copy(updateListWall(n))

  def updateListWall(n: Int): List[Cell] =  {
    if (n >= 26 && !cellList(n).hasWall) {
      println("Mauer wurde auf folgendes Feld gesetzt: " + n)
     cellList.updated(n, placeWall(n))
    }
    else {
      cellList
    }
  }

  def createGameBoard(): String = buildString(this.cellList)

}