package de.htwg.se.malefiz.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.malefiz.controller.controllerComponent.GameStates.GameState
import de.htwg.se.malefiz.controller.controllerComponent.State
import de.htwg.se.malefiz.controller.controllerComponent.Statements.Statements
import de.htwg.se.malefiz.model.gameBoardComponent.GameBoardInterface
import de.htwg.se.malefiz.playerModule.model.playerComponent.Player
import de.htwg.se.malefiz.util.Command

class SetPlayerCommand(playerNumber: Int, playerFigure: Int, cellNumber: Int, controller: Controller) extends Command {

  var memento: GameBoardInterface = controller.gameBoard
  var mDicedNumber: Int = controller.gameBoard.dicedNumber.get
  var mPlayersTurn: Player = controller.gameBoard.playersTurn.get
  var mStatementStatus: Statements = controller.gameBoard.statementStatus.get
  var mS: State[GameState] = controller.state.currentState

  override def doStep(): Unit = {
    controller.gameBoard = controller.gameBoard.removeActualPlayerAndFigureFromCell(playerNumber, playerFigure)

    if (controller.gameBoard.cellList(cellNumber).playerNumber != 0) {
      controller.gameBoard = controller.gameBoard.setPlayer(
        controller.gameBoard.cellList(cellNumber).playerNumber,
        controller.gameBoard.getHomeNr(controller.gameBoard.cellList(cellNumber).playerNumber,
            controller.gameBoard.cellList(cellNumber).figureNumber)
      )
      controller.gameBoard = controller.gameBoard.setFigure(
        controller.gameBoard.cellList(cellNumber).figureNumber,
        controller.gameBoard
          .getHomeNr(
            controller.gameBoard.cellList(cellNumber).playerNumber,
            controller.gameBoard.cellList(cellNumber).figureNumber
          )
      )
    }
    if (controller.gameBoard.cellList(cellNumber).hasWall)
      controller.placeOrRemoveWall(cellNumber, boolean = false)
    controller.gameBoard = controller.gameBoard.setPlayer(playerNumber, cellNumber)
    controller.gameBoard = controller.gameBoard.setFigure(playerFigure, cellNumber)
  }

  override def undoStep(): Unit = {

    val new_memento = controller.gameBoard
    val new_mDicedNumber = controller.gameBoard.dicedNumber
    val new_mPlayersTurn = controller.gameBoard.playersTurn.get
    val new_mS = controller.state
    val new_mStatementStatus = controller.gameBoard.statementStatus.get

    controller.gameBoard = memento
    controller.setDicedNumber(Some(mDicedNumber))
    controller.state.currentState = mS
    controller.setPlayersTurn(Option(mPlayersTurn))
    controller.setStatementStatus(mStatementStatus)

    memento = new_memento
    mDicedNumber = new_mDicedNumber.get
    mS = new_mS.currentState
    mPlayersTurn = new_mPlayersTurn
    mStatementStatus = new_mStatementStatus
  }

  override def redoStep(): Unit = doStep()
}
