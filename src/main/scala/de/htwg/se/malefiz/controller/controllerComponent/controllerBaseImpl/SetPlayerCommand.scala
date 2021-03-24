package de.htwg.se.malefiz.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.malefiz.controller.controllerComponent.GameStates.GameState
import de.htwg.se.malefiz.controller.controllerComponent.State
import de.htwg.se.malefiz.controller.controllerComponent.Statements.Statements
import de.htwg.se.malefiz.model.gameBoardComponent.GameBoardInterface
import de.htwg.se.malefiz.model.playerComponent.Player
import de.htwg.se.malefiz.util.Command

class SetPlayerCommand(playerNumber: Int, playerFigure: Int, cellNumber: Int, controller: Controller) extends Command {

  var memento: GameBoardInterface = controller.gameBoard
  var mDicedNumber: Int = controller.gameBoard.dicedNumber
  var mPlayersTurn: Player = controller.getGameBoard.playersTurn.get
  var mStatementStatus: Statements = controller.getGameBoard.statementStatus.get
  var mS: State[GameState] = controller.state.state

  override def doStep(): Unit = {
    controller.gameBoard = controller.gameBoard.removeActualPlayerAndFigureFromCell(playerNumber, playerFigure)

    if (controller.getGameBoard.cellList(cellNumber).playerNumber != 0) {
      controller.gameBoard = controller.gameBoard.setPlayer(
        controller.getGameBoard.cellList(cellNumber).playerNumber,
        controller.gameBoard
          .getHomeNr(
            controller.getGameBoard.cellList(cellNumber).playerNumber,
            controller.getGameBoard.cellList(cellNumber).figureNumber
          )
      )
      controller.gameBoard = controller.gameBoard.setFigure(
        controller.getGameBoard.cellList(cellNumber).figureNumber,
        controller.gameBoard
          .getHomeNr(
            controller.getGameBoard.cellList(cellNumber).playerNumber,
            controller.getGameBoard.cellList(cellNumber).figureNumber
          )
      )
    }
    if (controller.getGameBoard.cellList(cellNumber).hasWall)
      controller.removeWall(cellNumber)
    controller.gameBoard = controller.gameBoard.setPlayer(playerNumber, cellNumber)
    controller.gameBoard = controller.gameBoard.setFigure(playerFigure, cellNumber)
  }

  override def undoStep(): Unit = {

    val new_memento = controller.gameBoard
    val new_mDicedNumber = controller.gameBoard.dicedNumber
    val new_mPlayersTurn = controller.gameBoard.playersTurn.get
    val new_mS = controller.state
    val new_mStatementStatus = controller.getGameBoard.statementStatus.get

    controller.gameBoard = memento
    controller.setDicedNumber(mDicedNumber)
    controller.state.state = mS
    controller.setPlayersTurn(Option(mPlayersTurn))
    controller.setStatementStatus(mStatementStatus)

    memento = new_memento
    mDicedNumber = new_mDicedNumber
    mS = new_mS.state
    mPlayersTurn = new_mPlayersTurn
    mStatementStatus = new_mStatementStatus
  }

  override def redoStep(): Unit = doStep()
}
