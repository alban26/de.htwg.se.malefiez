package de.htwg.se.malefiz.model

import scala.collection.mutable.{Map, Queue}
import scala.io.Source
import scala.io.StdIn.readLine

case class GameBoard(cellList: List[Cell], playerArray: Array[Player], gameBoardGraph: Map[Int, Set[Int]]) {

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

    val pArray = new Array[Player](numberOfPlayers)
    for (i <- pArray.indices) {
      println("Spieler " + (i+1) + " geben Sie ihren Namen ein: ")
      val name = readLine()
      println("Spieler " + (i+1) + " geben Sie ihre Farbe ein: ")
      val colour = readLine()
      pArray(i) = Player(i, name, colour, this.cellList)
      println(pArray(i).toString)
      for (x <- pArray(i).playerFigures.indices) {
        println(pArray(i).playerFigures(x))
      }
    }
    this.copy(playerArray = pArray)
    this
  }

  def showPlayerStats(pArray: Array[Player]) : GameBoard = {
    for (i <- pArray.indices) {
      println(pArray(i).toString)
      for (x <- pArray(i).playerFigures.indices) {
        println(pArray(i).playerFigures(x))
      }
    }
    this
  }


  def setPlayerFigure(playerNumber: Int, playerFigureNumber: Int, destinationCell: Int,
                      playerArray: Array[Player], cellList: List[Cell]): GameBoard = {
    val newGameBoard = GameBoard(cellList, playerArray,gameBoardGraph)
    for (i <- playerArray.indices) {
      for (y <- playerArray(i).playerFigures.indices) {
        if (playerArray(i).playerFigures(y).inCell == cellList(destinationCell)) {
          if (isPlayerFigureOnCell(destinationCell, cellList, playerArray)) {
            kickPlayerFigure(destinationCell, playerArray, cellList)
            newGameBoard.playerArray(playerNumber-1).playerFigures(playerFigureNumber-1).copy(y,cellList(destinationCell))
          }
          else {
            playerArray(i).playerFigures(y).copy(y,cellList(destinationCell))
          }
        }
      }
    }
    newGameBoard
  }

  def kickPlayerFigure(cellNumber: Int, playerArray: Array[Player], cellList: List[Cell]): Unit = {
    for (i <- playerArray.indices) {
      for (y <- playerArray(i).playerFigures.indices) {
        if (playerArray(i).playerFigures(y).inCell == cellList(cellNumber)) {
          playerArray(i).playerFigures(y).copy(y, cellList(playerArray(i).numberOfPlayer))
        }
      }
    }
  }

  def isPlayerFigureOnCell(destinationCell: Int, cellList: List[Cell], playerArray: Array[Player]): Boolean = {
    for (i <- playerArray.indices) {
      for(y <- playerArray(i).playerFigures.indices) {
        if (playerArray(i).playerFigures(y).inCell == cellList(destinationCell))
          true
      }
    }
    false
  }

  def visit(n: Int, graph: Map[Int, Set[Int]]): Unit = {
    var besucht : Set[Int] = Set().empty
    visitBF(n, graph, besucht)
  }

  def visitBF(v: Int, g: Map[Int, Set[Int]], besucht: Set[Int]): Unit = {
    var q : Queue[Int] = Queue().empty
    q.apply(v)

  }

  def setWall(n: Int, board: GameBoard): GameBoard =  {
    if (n >= 26 && !board.cellList(n).hasWall) {
      val nCell = board.copy(cellList(n).copy(hasWall = true))
      val nBoard = board.copy()
      println("Mauer wurde auf " + n + " gesetzt")
      board2
    } else {
      println("Mauer wurde nicht gesetzt")
      board
    }
  }

  def createGameBoard(): String = buildString(this.cellList)


}