package de.htwg.se.malefiz.controller.controllerComponent

import de.htwg.se.malefiz.aview.gui.SwingGui
import de.htwg.se.malefiz.controller.controllerComponent.GameStates.GameState
import de.htwg.se.malefiz.controller.controllerComponent.controllerBaseImpl.{SetPlayerCommand, SetWallCommand}
import de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl.Cube
import de.htwg.se.malefiz.util.UndoManager

import scala.swing.Publisher

trait ControllerInterface extends Publisher {

  def execute(string: String): Boolean

  def createPlayer(name: String): Unit

  def setPosisCellTrue(l: List[Int]): Unit

  def setPosisCellFalse(l: List[Int]): Unit

  def setPosisTrue(n: Int): Unit

  def setPosisFalse(n: Int): Unit

  def rollCube: Int
  def setPlayerFigure(playerNumber: Int, playerFigure: Int, cellNumber: Int): Unit

  def getFigure(pn: Int, fn:Int): Int

  def getPCells(startCell: Int, cubeNumber: Int): Unit

  def removeActualPlayerAndFigureFromCell(pN: Int, fN: Int): Unit

  def setFigure(fN: Int, cN: Int): Unit

  def setPlayer(pN: Int, cN: Int): Unit

  def setWall(n: Int): Unit

  def rWall(n: Int): Unit

  def gameBoardToString: String

  def undo: Unit

  def redo: Unit
}
