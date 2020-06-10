package de.htwg.se.malefiz.model

import java.awt.Color

import scala.collection.mutable.Map
import scala.io.Source

case class GameBoard(cellList: List[Cell]) {

  val cellConfigFile = "C:\\Users\\ALBAN\\Desktop\\AIN\\STUDIUM\\3.Semester\\Software Engineering\\de.htwg.se.malefiz\\src\\main\\scala\\de\\htwg\\se\\malefiz\\model\\mainCellConfiguration"
  val cellLinksFile = "C:\\Users\\ALBAN\\Desktop\\AIN\\STUDIUM\\3.Semester\\Software Engineering\\de.htwg.se.malefiz\\src\\main\\scala\\de\\htwg\\se\\malefiz\\model\\mainCellLinks"


  def getCellGraph(in: String) : Map[Int, Set[Int]] = {
    val source = Source.fromFile(in)
    val lines = source.getLines()
    val graph : Map[Int, Set[Int]] = Map.empty
    while (lines.hasNext) {
      val input = lines.next()
      val inputArray: Array[String] = input.split(" ")
      for (i <- 1 until inputArray.length) {
        updateCellGraph(inputArray(0).toInt, inputArray(i).toInt, graph)
      }
    }
    graph
  }

  def updateCellGraph(key: Int, value: Int, map: Map[Int, Set[Int]]) : Map[Int, Set[Int]] = {
    map.get(key)
      .map(_=> map(key) += value)
      .getOrElse(map(key) = Set[Int](value))
    map
  }

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


  def wall(n: Int): Cell = cellList(n).copy(hasWall = true)

  def player(n: Int, figure: PlayFigure): Cell = cellList(n).copy(figure = figure)

  def setWall(n: Int): GameBoard = copy(updateListWall(n))



  def setupFiguresH(liss: List[Cell], n: Int, fg: List[PlayFigure]): GameBoard = copy(updateListFigure(n, fg,liss))

  def setupFigures(spielerListe: List[String]): GameBoard = {
    val spieler = createPlayer(spielerListe.length-1,spielerListe)
    val figuren = createFiguresH(spieler)
    setupFiguresH(cellList,figuren.length-1,figuren)
  }

  def createPlayer(n: Int, name: List[String]): List[Player] = {
    if (n == 0) {
      List(Player(n, name(n)))
    } else {
      createPlayer(n - 1, name) :+ Player(n, name(n))
    }
  }

  val colorList: List[Color] = List(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW)

  def createFiguresH(playerList: List[Player]): List[PlayFigure] = {
    createFigures(playerList.length * 5 - 1, playerList, colorList, 0, 1)
  }

  def createFigures(n: Int, playerList: List[Player], colorList: List[Color], m: Int, z: Int): List[PlayFigure] = {
    if (n == 0) {
      List(PlayFigure(n, playerList(m), colorList(m)))
    }
    else if (z % 5 == 0) {
      createFigures(n - 1, playerList, colorList, m + 1, z + 1) :+ PlayFigure(n, playerList(m), colorList(m))
    } else {
      createFigures(n - 1, playerList, colorList, m, z + 1) :+ PlayFigure(n, playerList(m), colorList(m))
    }
  }

  def updateListFigure(n: Int, figureList: List[PlayFigure], lis: List[Cell]): List[Cell] = {
    if (n == 0) {
      lis.updated(n, player(n, figureList(n)))
    }
    else {
      updateListFigure(n - 1, figureList, lis.updated(n, player(n, figureList(n))))
    }
  }

  def updateListWall(n: Int): List[Cell] = cellList.updated(n,wall(n))

  def createGameBoard(): String = buildString(cellList)

}