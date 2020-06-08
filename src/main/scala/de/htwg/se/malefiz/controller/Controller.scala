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

  def setPlayer(n: String): Unit = {
    gameBoard = gameBoard.setPlayer(n)
    notifyObservers
  }

  def gameBoardToString: String = gameBoard.createGameBoard()

}
