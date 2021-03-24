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
import de.htwg.se.malefiz.controller.controllerComponent.GameStates.{
  GameState,
  Roll,
  SelectFigure,
  SetFigure,
  SetWall,
  Setup
}
import scala.swing.Publisher

class Controller @Inject() (var gameBoard: GameBoardInterface) extends ControllerInterface with Publisher {

  val injector: Injector = Guice.createInjector(new MalefizModule)
  val fileIo: FileIOInterface = injector.instance[FileIOInterface]
  val mementoGameboard: GameBoardInterface = gameBoard
  val state: GameState = GameState(this)
  val undoManager = new UndoManager

  override def setGameBoard(gb: GameBoardInterface): Unit = this.gameBoard = gb

  override def getGameBoard: GameBoardInterface = gameBoard

  override def execute(input: String): Unit = state.run(input)

  override def resetPossibleCells(): Unit = gameBoard = gameBoard.clearPossibleCells

  override def resetGameBoard(): Unit = gameBoard = mementoGameboard

  override def createPlayer(name: String): Unit = {
    gameBoard = gameBoard.createPlayer(name)
    publish(new GameBoardChanged)
  }

  override def setPossibleCellsTrue(availableCells: List[Int]): Unit = {
    gameBoard = gameBoard.setPosiesCellTrue(availableCells)
    publish(new GameBoardChanged)
  }

  override def setPossibleCellsFalse(l: List[Int]): Unit = {
    gameBoard = gameBoard.setPosiesCellFalse(l)
    publish(new GameBoardChanged)
  }

  override def setPossibleFiguresTrue(playerNumber: Int): Unit = {
    gameBoard = gameBoard.execute(gameBoard.setPosiesTrue, playerNumber)
    publish(new GameBoardChanged)
  }

  override def setPossibleFiguresFalse(n: Int): Unit = {
    gameBoard = gameBoard.execute(gameBoard.setPosiesFalse, n)
    publish(new GameBoardChanged)
  }

  override def rollCube: Int = Cube().getRandomNumber

  override def setPlayerFigure(playerNumber: Int, playerFigure: Int, cellNumber: Int): Unit = {
    undoManager.doStep(new SetPlayerCommand(playerNumber, playerFigure, cellNumber, this))
    publish(new GameBoardChanged)
  }

  override def getFigurePosition(playerNumber: Int, figureNumber: Int): Int = {
    val position = gameBoard.getPlayerFigure(playerNumber, figureNumber)
    position
  }

  override def calculatePath(startCell: Int, diceNumber: Int): Unit = {
    gameBoard = gameBoard.getPossibleCells(startCell, diceNumber)
    publish(new GameBoardChanged)
  }

  override def removeActualPlayerAndFigureFromCell(playerNumber: Int, figureNumber: Int): Unit = {
    gameBoard = gameBoard.removeActualPlayerAndFigureFromCell(playerNumber, figureNumber)
    publish(new GameBoardChanged)
  }

  override def setFigure(figureNumber: Int, cellNumber: Int): Unit = {
    gameBoard = gameBoard.setFigure(figureNumber, cellNumber)
    publish(new GameBoardChanged)
  }

  override def setPlayer(playerNumber: Int, cellNumber: Int): Unit = {
    gameBoard = gameBoard.setPlayer(playerNumber, cellNumber)
    publish(new GameBoardChanged)
  }

  override def setWall(n: Int): Unit = {
    undoManager.doStep(new SetWallCommand(n, this))
    publish(new GameBoardChanged)
  }

  override def removeWall(n: Int): Unit = {
    gameBoard = gameBoard.removeWall(n)
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

  override def setDicedNumber(dicedNumber: Int): Unit = {
    gameBoard = gameBoard.setDicedNumber(dicedNumber)
    publish(new GameBoardChanged)
  }

  override def setPossibleCells(pC: Set[Int]): GameBoardInterface =
    gameBoard.setPossibleCell(pC)

  override def weHaveAWinner(): Unit =
    publish(new Winner)

  override def setPlayersTurn(player: Option[Player]): Unit = {
    gameBoard = gameBoard.setPlayersTurn(player)
    publish(new GameBoardChanged)
  }

  override def setSelectedFigure(playerNumber: Int, figureNumber: Int): Unit = {
    gameBoard = gameBoard.setSelectedFigure(playerNumber, figureNumber)
    publish(new GameBoardChanged)
  }

  override def setStateNumber(stateNumber: Int): Unit = {
    gameBoard = gameBoard.setStateNumber(stateNumber)
    publish(new GameBoardChanged)
  }

  override def setStatementStatus(statement: Statements): Unit = {
    gameBoard = gameBoard.setStatementStatus(statement)
    publish(new GameBoardChanged)
  }

  override def getGameState: GameState = this.state

  override def nextPlayer(playerList: List[Player], playerNumber: Int): Option[Player] =
    gameBoard.nextPlayer(playerList, playerNumber)

  override def save(): Unit = {
    fileIo.save(gameBoard, this)
    publish(new GameBoardChanged)
  }

  override def load(): Unit = {
    val newController = fileIo.loadController
    val stateNr = newController.getStateNumber.get

    this.setGameBoard(newController.getGameBoard)
    this.setPossibleCells(newController.getPossibleCells)
    this.setPlayersTurn(newController.getPlayersTurn)
    this.setSelectedFigure(newController.getSelectedFigure.get._1, newController.getSelectedFigure.get._2)

    stateNr match {
      case 1 =>
        this.state.nextState(Roll(this))
        this.setStatementStatus(Statements.nextPlayer)
      case 2 =>
        this.state.nextState(SelectFigure(this))
        this.setStatementStatus(Statements.selectFigure)
      case 3 =>
        this.state.nextState(SetFigure(this))
        this.setStatementStatus(Statements.selectField)
      case 4 =>
        this.state.nextState(Setup(this))
        this.setStatementStatus(Statements.addPlayer)
      case 5 =>
        this.state.nextState(SetWall(this))
        this.setStatementStatus(Statements.wall)
    }

    publish(new GameBoardChanged)
  }

}
