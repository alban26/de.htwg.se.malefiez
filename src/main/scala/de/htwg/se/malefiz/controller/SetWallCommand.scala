package de.htwg.se.malefiz.controller

import de.htwg.se.malefiz.util.Command

class SetWallCommand (n: Int, controller: Controller) extends Command{
  override def doStep: Unit = controller.gameBoard = controller.gameBoard.setWall(n)

  override def undoStep: Unit = controller.gameBoard = controller.gameBoard.rWall(n)

  override def redoStep: Unit = doStep
}
