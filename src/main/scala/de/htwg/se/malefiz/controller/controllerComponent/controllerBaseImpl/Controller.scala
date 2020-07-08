package de.htwg.se.malefiz.controller.controllerComponent.controllerBaseImpl

import com.google.inject.Injector
import com.google.inject.{Guice, Inject}
import de.htwg.se.malefiz.Malefiz.controller
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.malefiz.MalefizModule
import de.htwg.se.malefiz.aview.Tui
import de.htwg.se.malefiz.aview.gui.{EntryGui, SwingGui}
import de.htwg.se.malefiz.controller.controllerComponent.GameStates.{GameState, Roll}
import de.htwg.se.malefiz.controller.controllerComponent.{ControllerInterface, GameBoardChanged, State, Winner}
import de.htwg.se.malefiz.model.fileIoComponent.FileIOInterface
import de.htwg.se.malefiz.model.gameBoardComponent.GameboardInterface
import de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl.{Cell, Cube}
import de.htwg.se.malefiz.model.playerComponent.Player
import de.htwg.se.malefiz.util.UndoManager
import de.htwg.se.malefiz.controller.controllerComponent.GameStates.GameState
import de.htwg.se.malefiz.controller.controllerComponent.Statements.Statements

import scala.collection.mutable
import scala.swing.Publisher

class Controller @Inject() (var gameBoard: GameboardInterface) extends ControllerInterface with Publisher {

  var statementStatus: Statements = addPlayer

  def setGameBoard(gb: GameboardInterface): Unit = {
    this.gameBoard = gb;
  }

  def getGameBoard: GameboardInterface = {
    gameBoard
  }

  val injector: Injector = Guice.createInjector(new MalefizModule)
  val fileIo = injector.instance[FileIOInterface]

  val mementoGameboard: GameboardInterface = gameBoard
  var playersTurn: Player = _
  var dicedNumber: Int = _
  var selectedFigure: (Int, Int) = _

  val s: GameState = GameState(this)


  val undoManager = new UndoManager

  //var entryGui = new EntryGui(this)
 //var gui = new SwingGui(this)

  def execute(string: String): Boolean = {
    s.run(string)
    true
  }

  def resetGameboard: Unit = {
    gameBoard = mementoGameboard
  }

  def createPlayer(name: String): Unit = {
    gameBoard = gameBoard.createPlayer(name)
    publish(new GameBoardChanged)
  }

  def setPosisCellTrue(l: List[Int]): Unit = {
    gameBoard = gameBoard.setPosiesCellTrue(l)
    publish(new GameBoardChanged)
  }

  def setPosisCellFalse(l: List[Int]): Unit = {
    gameBoard = gameBoard.setPosiesCellFalse(l)
    publish(new GameBoardChanged)
  }

  def setPosisTrue(n: Int): Unit = {
    gameBoard = gameBoard.execute(gameBoard.setPosiesTrue,n)
    publish(new GameBoardChanged)
  }

  def setPosisFalse(n: Int): Unit = {
    gameBoard = gameBoard.execute(gameBoard.setPosiesFalse,n)
    publish(new GameBoardChanged)
  }

  def rollCube: Int = {
    Cube().getRandomNumber
  }

  def setPlayerFigure(playerNumber: Int, playerFigure: Int, cellNumber: Int): Unit = {
    undoManager.doStep(new SetPlayerCommand(playerNumber,playerFigure,cellNumber,this))
    publish(new GameBoardChanged)
  }

  def getFigure(pn: Int, fn:Int) : Int = {
    val position = gameBoard.getPlayerFigure(pn, fn)
    position
  }

  def getPCells(startCell: Int, cubeNumber: Int) : Unit = {
    gameBoard = gameBoard.getPossibleCells(startCell, cubeNumber)
    publish(new GameBoardChanged)
  }

  def removeActualPlayerAndFigureFromCell(pN: Int, fN: Int): Unit = {
    gameBoard = gameBoard.removeActualPlayerAndFigureFromCell(pN,fN)
    publish(new GameBoardChanged)
  }

  def setFigure(fN: Int, cN: Int): Unit = {
    gameBoard = gameBoard.setFigure(fN,cN)
    publish(new GameBoardChanged)
  }

  def setPlayer(pN: Int, cN: Int): Unit = {
    gameBoard = gameBoard.setPlayer(pN, cN)
    publish(new GameBoardChanged)
  }

  def setWall(n: Int): Unit = {
    undoManager.doStep(new SetWallCommand(n,this))
    publish(new GameBoardChanged)
  }

  def rWall(n: Int): Unit = {
    gameBoard = gameBoard.rWall(n)
    publish(new GameBoardChanged)
  }

  def gameBoardToString: String = gameBoard.createGameBoard()

  def undo: Unit = {
    undoManager.undoStep
    publish(new GameBoardChanged)
  }

  def redo: Unit = {
    undoManager.redoStep
    publish(new GameBoardChanged)
  }

  def weHaveAWinner() : Unit = {
    publish(new Winner)
  }

  override def getCellList: List[Cell] = gameBoard.getCellList

  override def getPlayer: List[Player] = gameBoard.getPlayer

  override def getGameBoardGraph: mutable.Map[Int, Set[Int]] = gameBoard.getGameBoardGraph

  override def getPossibleCells: Set[Int] = gameBoard.getPossibleCells

  override def getDicedNumber: Int = this.dicedNumber

  override def getPlayersTurn: Player = this.playersTurn

  override def getSelectedFigure: (Int, Int) = this.selectedFigure

  override def getGameState: GameState = this.s

  override def getUndoManager: UndoManager = this.undoManager

 // override def getGui: SwingGui = this.gui

  //override def getEntryGui: EntryGui = this.entryGui

  def getStatement: Statements = statementStatus

  override def setSelectedFigures(n: Int, m: Int): Boolean = {
    this.selectedFigure = (n,m)
    true
  }

  override def setStatementStatus(statements: Statements): Boolean = {
    this.statementStatus = statements
    true
  }

  override def setPlayersTurn(player: Player): Boolean = {
    this.playersTurn = player
    true
  }

  override def setDicedNumber(n: Int): Boolean = {
    this.dicedNumber = n
    true
  }

  override def nextPlayer(list: List[Player], n: Int): Player = {
    gameBoard.nextPlayer(list,n)
  }

  override def save: Unit = {
    fileIo.save(gameBoard, this)
    publish(new GameBoardChanged)
  }

  override def load: Unit = {
    val c = fileIo.loadController
    println(c.getDicedNumber)
    println(c.getPlayersTurn)
    this.setGameBoard(c.getGameBoard)
    this.setDicedNumber(c.getDicedNumber)
    this.setPlayersTurn(c.getPlayersTurn)
    this.s.nextState(Roll(this))
    publish(new GameBoardChanged)
  }
}
