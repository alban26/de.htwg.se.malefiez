package de.htwg.se.malefiz.controller

import de.htwg.se.malefiz.model.{GameBoard, Player}
import de.htwg.se.malefiz.util.{Observable, Observer}

class Controller(var gameBoard: GameBoard) extends Observable {

  def createGameBoardGraph: Unit = {
    gameBoard =  GameBoard(gameBoard.list)
    notifyObservers

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
