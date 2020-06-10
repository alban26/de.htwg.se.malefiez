package de.htwg.se.malefiz.controller



import de.htwg.se.malefiz.model.{Cell, GameBoard, PlayFigure, Player}
import de.htwg.se.malefiz.util.{Observable, Observer}

class Controller(var gameBoard: GameBoard) extends Observable {

  def createGameBoardGraph: Unit = {
    gameBoard =  GameBoard(gameBoard.cellList)
    notifyObservers

  }


  def setWall(n: Int): Unit = {
    gameBoard = gameBoard.setWall(n)
    notifyObservers
  }

  def setupFigures(spielerListe: List[String]): Unit = {
    gameBoard = gameBoard.setupFigures(spielerListe)
  }


  def gameBoardToString: String = gameBoard.createGameBoard()

}
