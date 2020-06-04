package de.htwg.se.malefiz.controller

import de.htwg.se.malefiz.model.{GameBoard, Player}
import de.htwg.se.malefiz.util.{Observable, Observer}
import scala.io.StdIn.readLine

class Controller(var gameBoard: GameBoard) extends Observable {

  def createGameBoardGraph: Unit = {
    gameBoard =  GameBoard(gameBoard.list)
    notifyObservers

  }

  def createPlayerArray(numberOfPlayers: Int) : Array[Player] = {
    val playerArray = new Array[Player](numberOfPlayers)
    for (i <- playerArray.indices) {
      println("Spieler " + (i+1) + "geben Sie ihren Namen ein: ")
      val name = readLine()
      println("Spieler " + (i+1) + "geben Sie ihre Farbe ein: ")
      val colour = readLine()
      playerArray(i) = Player(name, colour, i)
    }
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
