package de.htwg.se.malefiz.controller

import de.htwg.se.malefiz.util.Command

class SetPlayerCommand(playerNumber: Int, playerFigure: Int, cellNumber: Int, controller: Controller) extends Command  {
  val safeKickedPlayer: (Int, Int) = (controller.gameBoard.cellList(cellNumber).playerNumber,controller.gameBoard.cellList(cellNumber).figureNumber)
  var kicked: Boolean = false
  override def doStep: Unit = {
    controller.gameBoard = controller.gameBoard.removeActualPlayerAndFigureFromCell(playerNumber,playerFigure)

    if(controller.gameBoard.cellList(cellNumber).playerNumber != 0) {

      controller.gameBoard = controller.gameBoard.setPlayer(controller.gameBoard.cellList(cellNumber).playerNumber,
        controller.gameBoard.getHomeNr(controller.gameBoard.cellList(cellNumber).playerNumber,controller.gameBoard.cellList(cellNumber).figureNumber))
      controller.gameBoard = controller.gameBoard.setFigure(controller.gameBoard.cellList(cellNumber).figureNumber,
        controller.gameBoard.getHomeNr(controller.gameBoard.cellList(cellNumber).playerNumber,controller.gameBoard.cellList(cellNumber).figureNumber))
      kicked = true
    }

    controller.gameBoard = controller.gameBoard.setPlayer(playerNumber, cellNumber)
    controller.gameBoard = controller.gameBoard.setFigure(playerFigure, cellNumber)

    controller.playersTurn = controller.gameBoard.nextPlayer(controller.player,controller.playersTurn.playerNumber-1)
    controller.playingState = PlayingState.ROLL
  }

  override def undoStep: Unit = {

    controller.gameBoard = controller.gameBoard.removeActualPlayerAndFigureFromCell(playerNumber,playerFigure)

    controller.gameBoard = controller.gameBoard.setPlayer(playerNumber, controller.gameBoard.getHomeNr(playerNumber,playerFigure))
    controller.gameBoard = controller.gameBoard.setFigure(playerFigure, controller.gameBoard.getHomeNr(playerNumber,playerFigure))

    if(kicked){
      println(safeKickedPlayer)
      controller.gameBoard = controller.gameBoard.removeActualPlayerAndFigureFromCell(safeKickedPlayer._1,safeKickedPlayer._2)
      controller.gameBoard = controller.gameBoard.setPlayer(safeKickedPlayer._1, cellNumber)
      controller.gameBoard = controller.gameBoard.setFigure(safeKickedPlayer._2, cellNumber)

    }
  }

  override def redoStep: Unit = doStep
}
