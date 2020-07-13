package de.htwg.se.malefiz.controller.controllerComponent.controllerBaseImpl

import com.google.inject.Injector
import com.google.inject.{Guice, Inject}
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.malefiz.MalefizModule
import de.htwg.se.malefiz.controller.controllerComponent.Statements._
import de.htwg.se.malefiz.controller.controllerComponent.{ControllerInterface, GameBoardChanged, Statements, Winner}
import de.htwg.se.malefiz.model.fileIoComponent.FileIOInterface
import de.htwg.se.malefiz.model.gameBoardComponent.GameBoardInterface
import de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl.{Cell, Cube}
import de.htwg.se.malefiz.model.playerComponent.Player
import de.htwg.se.malefiz.util.UndoManager
import de.htwg.se.malefiz.controller.controllerComponent.GameStates.{GameState, Roll, SelectFigure, SetFigure, SetWall, Setup}
import scala.swing.Publisher

class Controller @Inject() (var gameBoard: GameBoardInterface) extends ControllerInterface with Publisher {

  var statementStatus: Statements = addPlayer
  var stateNumber: Int = _
  var selectedFigure: (Int, Int) = _
  var playersTurn: Player = _
  var dicedNumber: Int = _

  val injector: Injector = Guice.createInjector(new MalefizModule)
  val fileIo: FileIOInterface = injector.instance[FileIOInterface]
  val mementoGameboard: GameBoardInterface = gameBoard
  val state: GameState = GameState(this)
  val undoManager = new UndoManager

  override def setStateNumber(n: Int): Unit = this.stateNumber = n

  override def getStateNumber: Int = stateNumber

  override def setGameBoard(gb: GameBoardInterface): Unit = this.gameBoard = gb

  override def getGameBoard: GameBoardInterface = gameBoard

  override def execute(string: String): Unit = state.run(string)

  override def resetPossibleCells(): Unit = gameBoard = gameBoard.clearPossibleCells

  override def resetGameBoard(): Unit = gameBoard = mementoGameboard

  override def createPlayer(name: String): Unit = {
    gameBoard = gameBoard.createPlayer(name)
    publish(new GameBoardChanged)
  }

  override def setPossibleCellsTrue(l: List[Int]): Unit = {
    gameBoard = gameBoard.setPosiesCellTrue(l)
    publish(new GameBoardChanged)
  }

  override def setPossibleCellsFalse(l: List[Int]): Unit = {
    gameBoard = gameBoard.setPosiesCellFalse(l)
    publish(new GameBoardChanged)
  }

  override def setPossibleFiguresTrue(n: Int): Unit = {
    gameBoard = gameBoard.execute(gameBoard.setPosiesTrue,n)
    publish(new GameBoardChanged)
  }

  override def setPossibleFiguresFalse(n: Int): Unit = {
    gameBoard = gameBoard.execute(gameBoard.setPosiesFalse,n)
    publish(new GameBoardChanged)
  }

  override def rollCube: Int = Cube().getRandomNumber

  override def setPlayerFigure(playerNumber: Int, playerFigure: Int, cellNumber: Int): Unit = {
    undoManager.doStep(new SetPlayerCommand(playerNumber,playerFigure,cellNumber,this))
    publish(new GameBoardChanged)
  }

  override def getFigure(pn: Int, fn:Int) : Int = {
    val position = gameBoard.getPlayerFigure(pn, fn)
    position
  }

  override def calculatePath(startCell: Int, cubeNumber: Int) : Unit = {
    gameBoard = gameBoard.getPossibleCells(startCell, cubeNumber)
    publish(new GameBoardChanged)
  }

  override def removeActualPlayerAndFigureFromCell(pN: Int, fN: Int): Unit = {
    gameBoard = gameBoard.removeActualPlayerAndFigureFromCell(pN,fN)
    publish(new GameBoardChanged)
  }

  override def setFigure(fN: Int, cN: Int): Unit = {
    gameBoard = gameBoard.setFigure(fN,cN)
    publish(new GameBoardChanged)
  }

  override def setPlayer(pN: Int, cN: Int): Unit = {
    gameBoard = gameBoard.setPlayer(pN, cN)
    publish(new GameBoardChanged)
  }

  override def setWall(n: Int): Unit = {
    undoManager.doStep(new SetWallCommand(n,this))
    publish(new GameBoardChanged)
  }

  override def removeWall(n: Int): Unit = {
    gameBoard = gameBoard.rWall(n)
    publish(new GameBoardChanged)
  }

  override def gameBoardToString: String = gameBoard.createGameBoard()

  override def undo(): Unit = {
    undoManager.undoStep()
    publish(new GameBoardChanged)
  }

  override def redo(): Unit = {
    undoManager.redoStep()
    publish(new GameBoardChanged)
  }

  override def setPossibleCells(pC: Set[Int]): GameBoardInterface = {
    gameBoard.setPossibleCell(pC)
  }

  override def weHaveAWinner() : Unit = {
    publish(new Winner)
  }

  override def getCellList: List[Cell] = gameBoard.getCellList

  override def getPlayer: List[Player] = gameBoard.getPlayer

  override def getPossibleCells: Set[Int] = gameBoard.getPossibleCells

  override def getDicedNumber: Int = this.dicedNumber

  override def getPlayersTurn: Player = this.playersTurn

  override def getSelectedFigure: (Int, Int) = this.selectedFigure

  override def getGameState: GameState = this.state

  override def getStatement: Statements = statementStatus

  override def setSelectedFigures(n: Int, m: Int): Unit = this.selectedFigure = (n,m)

  override def setStatementStatus(statements: Statements): Unit = this.statementStatus = statements

  override def setPlayersTurn(player: Player): Unit = this.playersTurn = player

  override def setDicedNumber(n: Int): Unit = this.dicedNumber = n

  override def nextPlayer(list: List[Player], n: Int): Player = gameBoard.nextPlayer(list,n)

  override def save(): Unit = {
    fileIo.save(gameBoard, this)
    publish(new GameBoardChanged)
  }

  override def load(): Unit = {
    val cNeu = fileIo.loadController
    val stateNr = cNeu.getStateNumber

    this.setGameBoard(cNeu.getGameBoard)
    this.setPossibleCells(cNeu.getPossibleCells)
    this.setDicedNumber(cNeu.getDicedNumber)
    this.setPlayersTurn(cNeu.getPlayersTurn)
    this.setSelectedFigures(cNeu.getSelectedFigure._1,cNeu.getSelectedFigure._2)

    stateNr match {
      case 1 => this.state.nextState(Roll(this))
        this.statementStatus = Statements.nextPlayer
      case 2 => this.state.nextState(SelectFigure(this))
        this.statementStatus = selectFigure
      case 3 => this.state.nextState(SetFigure(this))
        this.statementStatus = selectField
      case 4 => this.state.nextState(Setup(this))
        this.statementStatus = addPlayer
      case 5 => this.state.nextState(SetWall(this))
        this.statementStatus = wall
    }

    publish(new GameBoardChanged)
  }

}
