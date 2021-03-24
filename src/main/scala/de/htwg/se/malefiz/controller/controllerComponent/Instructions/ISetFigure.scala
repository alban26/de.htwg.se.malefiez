package de.htwg.se.malefiz.controller.controllerComponent.Instructions

import de.htwg.se.malefiz.controller.controllerComponent.GameStates.{Roll, SelectFigure, SetWall, Setup}
import de.htwg.se.malefiz.controller.controllerComponent.Instructions.ISelectFigure.Handler0
import de.htwg.se.malefiz.controller.controllerComponent.Statements._
import de.htwg.se.malefiz.controller.controllerComponent.{InstructionTrait, Request, StatementRequest, Statements}

object ISetFigure extends InstructionTrait {

  val set1: Handler0 = {
    case Request(inputList, gameState, controller)
        if !controller.getGameBoard
          .cellList(inputList.head.toInt)
          .hasWall && (inputList.head.toInt != 131) && controller.getGameBoard.possibleCells.contains(
          inputList.head.toInt
        ) && controller.getGameBoard
          .cellList(inputList.head.toInt)
          .playerNumber != controller.getGameBoard.playersTurn.get.playerNumber =>
      controller.setPlayerFigure(
        controller.getGameBoard.selectedFigure.get._1,
        controller.getGameBoard.selectedFigure.get._2,
        inputList.head.toInt
      )
      Request(inputList, gameState, controller)
  }

  val select1: Handler1 = {
    case Request(inputList, gameState, controller)
        if inputList.length == 2 && inputList.head.toInt == controller.getGameBoard.selectedFigure.get._1 && inputList(
          1
        ).toInt == controller.getGameBoard.selectedFigure.get._2 =>
      controller.setPossibleCellsFalse(controller.getGameBoard.possibleCells.toList)

      controller.resetPossibleCells()
      gameState.nextState(SelectFigure(controller))
      controller.setStatementStatus(changeFigure)
      Statements.value(StatementRequest(controller))
  }

  val set2: Handler0 = {
    case Request(inputList, gameState, controller)
        if controller.getGameBoard.cellList(inputList.head.toInt).hasWall && controller.getGameBoard.possibleCells
          .contains(
            inputList.head.toInt
          ) =>
      controller.setPlayerFigure(
        controller.getGameBoard.selectedFigure.get._1,
        controller.getGameBoard.selectedFigure.get._2,
        inputList.head.toInt
      )
      Request(inputList, gameState, controller)
  }

  val set3: Handler1 = {
    case Request(inputList, gameState, controller) =>
      controller.setDicedNumber(0)
      controller.setPossibleFiguresFalse(controller.getGameBoard.playersTurn.get.playerNumber)
      controller.setPossibleCellsFalse(controller.getGameBoard.possibleCells.toList)
      controller.resetPossibleCells()
      controller.setPlayersTurn(
        controller.nextPlayer(controller.getGameBoard.players, controller.getGameBoard.playersTurn.get.playerNumber - 1)
      )
      gameState.nextState(Roll(controller))
      controller.setStatementStatus(nextPlayer)
      Statements.value(StatementRequest(controller))
  }

  val set4: Handler1 = {
    case Request(inputList, gameState, controller) =>
      gameState.nextState(SetWall(controller))
      controller.setStatementStatus(wall)
      controller.setPossibleFiguresFalse(controller.getGameBoard.playersTurn.get.playerNumber)
      controller.setPossibleCellsFalse(controller.getGameBoard.possibleCells.toList)
      controller.resetPossibleCells()
      Statements.value(StatementRequest(controller))
  }
  val set5: Handler0 = {
    case Request(inputList, gameState, controller) if inputList.head.toInt == 131 =>
      Request(inputList, gameState, controller)
  }

  val set6: Handler1 = {
    case Request(inputList, gameState, controller) =>
      gameState.nextState(Setup(controller))
      controller.setStatementStatus(won)
      controller.weHaveAWinner()
      Statements.value(StatementRequest(controller))
  }

  val set7: Handler0 = {
    case Request(inputList, gameState, controller)
        if !controller.getGameBoard.possibleCells.contains(inputList.head.toInt) || controller.getGameBoard
          .cellList(inputList.head.toInt)
          .playerNumber == controller.getGameBoard.playersTurn.get.playerNumber =>
      Request(inputList, gameState, controller)
  }

  val set8: Handler1 = {
    case Request(inputList, gameState, controller) =>
      controller.setStatementStatus(wrongField)
      Statements.value(StatementRequest(controller))
  }

  val set: PartialFunction[Request, String] = (set1
    .andThen(set3)
    .andThen(log))
    .orElse(select1.andThen(log))
    .orElse(set2.andThen(set4).andThen(log))
    .orElse(set5.andThen(set6).andThen(log))
    .orElse(set7.andThen(set8).andThen(log))

}
