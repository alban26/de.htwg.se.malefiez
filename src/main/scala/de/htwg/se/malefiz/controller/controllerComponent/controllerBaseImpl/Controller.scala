package de.htwg.se.malefiz.controller.controllerComponent.controllerBaseImpl

import com.google.inject.{Guice, Inject, Injector}
import de.htwg.se.malefiz.MalefizModule
import de.htwg.se.malefiz.controller.controllerComponent.GameStates._
import de.htwg.se.malefiz.controller.controllerComponent.Statements._
import de.htwg.se.malefiz.controller.controllerComponent.{ControllerInterface, GameBoardChanged, Statements, Winner}
import de.htwg.se.malefiz.model.fileIoComponent.FileIOInterface
import de.htwg.se.malefiz.model.gameBoardComponent.GameBoardInterface
import de.htwg.se.malefiz.model.playerComponent.Player
import de.htwg.se.malefiz.util.UndoManager
import net.codingwell.scalaguice.InjectorExtensions._

import scala.swing.Publisher

class Controller @Inject()(var gameBoard: GameBoardInterface) extends ControllerInterface with Publisher {

  val injector: Injector = Guice.createInjector(new MalefizModule)
  val fileIo: FileIOInterface = injector.instance[FileIOInterface]
  val mementoGameBoard: GameBoardInterface = gameBoard
  val state: GameState = GameState(this)
  val undoManager = new UndoManager

  override def gameBoardToString: String = gameBoard.returnGameBoardAsString()

  override def resetGameBoard(): Unit = gameBoard = mementoGameBoard

  override def setGameBoard(gb: GameBoardInterface): Unit = this.gameBoard = gb

  override def undo(): Unit = {
    undoManager.undoStep()
    publish(new GameBoardChanged)
  }

  override def redo(): Unit = {
    undoManager.redoStep()
    publish(new GameBoardChanged)
  }

  override def weHaveAWinner(): Unit = publish(new Winner)

  override def rollCube: Option[Int] = {
    gameBoard = gameBoard.rollDice()
    gameBoard.dicedNumber
  }

  override def setDicedNumber(dicedNumber: Int): Unit = gameBoard = gameBoard.setDicedNumber(dicedNumber)

  override def createPlayer(name: String): Unit = {
    gameBoard = gameBoard.createPlayer(name)
    publish(new GameBoardChanged)
  }

  override def nextPlayer(playerList: List[Player], playerNumber: Int): Option[Player] =
    gameBoard.nextPlayer(playerList, playerNumber)

  override def setPlayersTurn(player: Option[Player]): Unit = {
    gameBoard = gameBoard.setPlayersTurn(player)
    publish(new GameBoardChanged)
  }

  override def placePlayerFigure(playerNumber: Int, playerFigure: Int, cellNumber: Int): Unit = {
    undoManager.doStep(new SetPlayerCommand(playerNumber, playerFigure, cellNumber, this))
    publish(new GameBoardChanged)
  }

  override def setSelectedFigure(playerNumber: Int, figureNumber: Int): Unit = {
    gameBoard = gameBoard.setSelectedFigure(playerNumber, figureNumber)
    publish(new GameBoardChanged)
  }

  override def getFigurePosition(playerNumber: Int, figureNumber: Int): Int = {
    val position = gameBoard.getPlayerFigure(playerNumber, figureNumber)
    position
  }

  override def resetPossibleCells(): Unit = gameBoard = gameBoard.clearPossibleCells

  override def setStateNumber(stateNumber: Int): Unit = {
    gameBoard = gameBoard.setStateNumber(stateNumber)
    publish(new GameBoardChanged)
  }

  override def calculatePath(startCell: Int, diceNumber: Int): Unit = {
    gameBoard = gameBoard.getPossibleCells(startCell, diceNumber)
    publish(new GameBoardChanged)
  }

  override def removeActualPlayerAndFigureFromCell(playerNumber: Int, figureNumber: Int): Unit = {
    gameBoard = gameBoard.removeActualPlayerAndFigureFromCell(playerNumber, figureNumber)
    publish(new GameBoardChanged)
  }

  override def placeFigure(figureNumber: Int, cellNumber: Int): Unit = {
    gameBoard = gameBoard.setFigure(figureNumber, cellNumber)
    publish(new GameBoardChanged)
  }

  override def placePlayer(playerNumber: Int, cellNumber: Int): Unit = {
    gameBoard = gameBoard.setPlayer(playerNumber, cellNumber)
    publish(new GameBoardChanged)
  }

  override def placeOrRemoveWall(n: Int, boolean: Boolean): Unit = {
    boolean match {
      case true => undoManager.doStep(new SetWallCommand(n, this))
      case false => gameBoard = gameBoard.removeWall(n)
    }
    publish(new GameBoardChanged)
  }

  override def getGameState: GameState = this.state

  override def setStatementStatus(statement: Statements): Unit = {
    gameBoard = gameBoard.setStatementStatus(statement)
    publish(new GameBoardChanged)
  }

  override def setPossibleCells(possibleCells: Set[Int]): GameBoardInterface = gameBoard.setPossibleCell(possibleCells)

  override def setPossibleCellsTrueOrFalse(availableCells: List[Int]): Unit = {
    state.state match {
      case SelectFigure(controller) => gameBoard = gameBoard.setPosiesCellTrue(availableCells)
      case _ => gameBoard = gameBoard.setPosiesCellFalse(availableCells)
    }
    publish(new GameBoardChanged)
  }

  override def setPossibleFiguresTrueOrFalse(playerNumber: Int): Unit = {
    gameBoard = gameBoard.setPosiesTrueOrFalse(playerNumber, state.state.toString)
    publish(new GameBoardChanged)
  }

  override def execute(input: String): Unit = state.run(input)

  override def save(): Unit = {
    fileIo.save(this.gameBoard, this)
    publish(new GameBoardChanged)
  }

  override def load(): Unit = {
    val newController = fileIo.loadController
    val stateNr = newController.gameBoard.stateNumber.get

    this.setGameBoard(newController.gameBoard)
    this.setPossibleCells(newController.gameBoard.possibleCells)
    this.setPlayersTurn(newController.gameBoard.playersTurn)
    this.setSelectedFigure(
      newController.gameBoard.selectedFigure.get._1,
      newController.gameBoard.selectedFigure.get._2
    )

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
