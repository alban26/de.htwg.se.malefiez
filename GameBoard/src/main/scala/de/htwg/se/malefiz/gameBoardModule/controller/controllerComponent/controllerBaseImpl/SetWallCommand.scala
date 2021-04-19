package de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.malefiz.gameBoardModule.util.Command

class SetWallCommand (cellNumber: Int, controller: Controller) extends Command {

  var memento = controller.gameBoard
  override def doStep(): Unit = {
    controller.gameBoard = controller.gameBoard.setWall(cellNumber)
  }

  override def undoStep(): Unit = {
    val new_memento = controller.gameBoard
    controller.gameBoard = memento
    memento = new_memento
  }

  override def redoStep(): Unit = doStep()

}
