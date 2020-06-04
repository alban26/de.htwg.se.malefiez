package de.htwg.se.malefiz.controller



import de.htwg.se.malefiz.model.GameBoard
import de.htwg.se.malefiz.util.{Observable,Observer}

class Controller(var gameBoard: GameBoard) extends Observable {

  def createGameBoardGraph: Unit = {
    gameBoard =  GameBoard()
    notifyObservers

  }



  def gameBoardToString: String = gameBoard.createGameBoard()



}
