package de.htwg.se.malefiz.controller



import de.htwg.se.malefiz.model.GameBoard
import de.htwg.se.malefiz.util.Observable
class Controller(var gameBoard: GameBoard) {

  def createGameBoardGraph: Unit = {
    gameBoard =  GameBoard()
  }

  def gameBoardToString: String = gameBoard.createGameBoard()



}
