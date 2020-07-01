package de.htwg.se.malefiz.controller

import de.htwg.se.malefiz.util.Command

class SetWallCommand (n: Int, controller: Controller) extends Command{
  var memento = controller.gameBoard
  override def doStep: Unit = {
    controller.gameBoard = controller.gameBoard.setWall(n)
  }

  override def undoStep: Unit = {
    val new_memento = controller.gameBoard
    controller.gameBoard = memento
    memento = new_memento
  }


  override def redoStep: Unit = doStep
}
