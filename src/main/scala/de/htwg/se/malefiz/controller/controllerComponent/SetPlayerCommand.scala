package de.htwg.se.malefiz.controller.controllerComponent

import de.htwg.se.malefiz.util.Command

class SetPlayerCommand(playerNumber: Int, playerFigure: Int, cellNumber: Int, controller: Controller) extends Command  {
  var memento = controller.gameBoard


  override def doStep: Unit = {
    controller.gameBoard = controller.gameBoard.removeActualPlayerAndFigureFromCell(playerNumber,playerFigure)

    if(controller.gameBoard.cellList(cellNumber).playerNumber != 0) {
      controller.gameBoard = controller.gameBoard.setPlayer(controller.gameBoard.cellList(cellNumber).playerNumber,
        controller.gameBoard.getHomeNr(controller.gameBoard.cellList(cellNumber).playerNumber,controller.gameBoard.cellList(cellNumber).figureNumber))
      controller.gameBoard = controller.gameBoard.setFigure(controller.gameBoard.cellList(cellNumber).figureNumber,
        controller.gameBoard.getHomeNr(controller.gameBoard.cellList(cellNumber).playerNumber,controller.gameBoard.cellList(cellNumber).figureNumber))
    }
    if(controller.gameBoard.cellList(cellNumber).hasWall) {
      controller.rWall(cellNumber)
    }
    controller.gameBoard = controller.gameBoard.setPlayer(playerNumber, cellNumber)
    controller.gameBoard = controller.gameBoard.setFigure(playerFigure, cellNumber)

  }

  override def undoStep: Unit = {
    val new_memento = controller.gameBoard
    controller.gameBoard = memento
    memento = new_memento
  }

  override def redoStep: Unit = doStep
}
