package de.htwg.se.malefiz.controller.controllerComponent

import de.htwg.se.malefiz.aview.gui.{EntryGui, SwingGui}
import de.htwg.se.malefiz.controller.controllerComponent.GameStates.GameState
import de.htwg.se.malefiz.controller.controllerComponent.Statements.Statements
import de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl.Cell
import de.htwg.se.malefiz.model.playerComponent.Player
import de.htwg.se.malefiz.util.UndoManager

import scala.collection.mutable.Map
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

  def getCellList: List[Cell]
  def getPlayer: List[Player]
  def getGameBoardGraph: Map[Int, Set[Int]]
  def getPossibleCells: Set[Int]

  def getDicedNumber: Int
  def getPlayersTurn: Player

  def getSelectedFigure: (Int, Int)

  def getGameState: GameState
  def getStatement: Statements

  def getUndoManager: UndoManager
  def getGui: SwingGui
  def getEntryGui: EntryGui
}

import scala.swing.Button
import scala.swing.event.Event

class GameBoardChanged extends Event
case class ButtonClicked(source: Button) extends Event
class changeWall extends Event
class Winner extends Event
// class CandidatesChanged extends Event
