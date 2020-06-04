package de.htwg.se.malefiz.controller

import de.htwg.se.malefiz.model.{Cell, Cube, GameBoard, ListCreator, Player}
import de.htwg.se.malefiz.util.{Observable, Observer}

import scala.collection.immutable.Queue
import scala.io.StdIn.readLine

class Controller(var gameBoard: GameBoard) extends Observable {
  val list : List[Cell] = ListCreator().getCellList

  def rollCube: Int = {
    gameBoard.
  }

  def createGameBoardGraph: Unit = {
    gameBoard =  GameBoard(gameBoard.list)
    notifyObservers
  }

  def visit(n: Int, graph: Map[Int, Set[Int]]): Unit = {
    var besucht : Set[Int] = Set().empty
    visitBF(n, graph, besucht)
  }

  def visitBF(v: Int, g: Map[Int, Set[Int]], besucht: Set[Int]): Unit = {
    var q : Queue[Int] = Queue().empty
    q.apply(v)



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

  def setPlayerFigure(destinationCell: Int, playerArray: Array[Player], cellList: List[Cell]): Unit = {
    for (i <- playerArray.indices) {
      for (y <- playerArray(i).playerFigures.indices) {
        if (playerArray(i).playerFigures(y).inCell == cellList(destinationCell)) {
          if (isPlayerFigureOnCell(destinationCell, cellList, playerArray)) {
              kickPlayerFigure(destinationCell, playerArray, cellList)
              playerArray(i).playerFigures(y).copy(y,cellList(destinationCell))
            }
          else {
            playerArray(i).playerFigures(y).copy(y,cellList(destinationCell))
          }
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

  def createPlayerArray(numberOfPlayers: Int) : Array[Player] = {
    val playerArray = new Array[Player](numberOfPlayers)
    for (i <- playerArray.indices) {
      println("Spieler " + (i+1) + " geben Sie ihren Namen ein: ")
      val name = readLine()
      println("Spieler " + (i+1) + " geben Sie ihre Farbe ein: ")
      val colour = readLine()
      playerArray(i) = Player(i, name, colour, list)
      println(playerArray(i).toString)
      for (x <- playerArray(i).playerFigures.indices) {
        println(playerArray(i).playerFigures(x))
      }
    }
    playerArray
  }


  def setWall(n: Int): Unit = {
    gameBoard = gameBoard.setWall(n)
    notifyObservers
  }

  def setPlayer(n: Int, player: Player): Unit = {
    gameBoard = gameBoard.setPlayer(n,player)
    notifyObservers
  }


  def gameBoardToString: String = gameBoard.createGameBoard()



}
