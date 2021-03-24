package de.htwg.se.malefiz.controller.controllerComponent.Instructions

import de.htwg.se.malefiz.controller.controllerComponent.GameStates.Roll
import de.htwg.se.malefiz.controller.controllerComponent.Statements._
import de.htwg.se.malefiz.controller.controllerComponent.{InstructionTrait, Request, StatementRequest, Statements}

object ISetWall extends InstructionTrait {

  val set1: Handler0 = {
    case Request(inputList, gameState, controller)
        if controller.getGameBoard.cellList(inputList.head.toInt).wallPermission && controller.getGameBoard
          .cellList(inputList.head.toInt)
          .playerNumber == 0 && !controller.getGameBoard.cellList(inputList.head.toInt).hasWall =>
      controller.setWall(inputList.head.toInt)
      Request(inputList, gameState, controller)
  }

  val set2: Handler0 = {
    case Request(inputList, gameState, controller)
        if !controller.getGameBoard.cellList(inputList.head.toInt).wallPermission || controller.getGameBoard
          .cellList(inputList.head.toInt)
          .playerNumber != 0 || controller.getGameBoard.cellList(inputList.head.toInt).hasWall =>
      Request(inputList, gameState, controller)
  }

  val set3: Handler1 = {
    case Request(inputList, gameState, controller) =>
      controller.setDicedNumber(0)
      controller.setPossibleFiguresFalse(controller.getGameBoard.playersTurn.get.playerNumber)
      controller.setPossibleCellsFalse(controller.getGameBoard.possibleCells.toList)
      controller.setPlayersTurn(
        controller.nextPlayer(controller.getGameBoard.players, controller.getGameBoard.playersTurn.get.playerNumber - 1)
      )
      gameState.nextState(Roll(controller))
      controller.setStatementStatus(nextPlayer)
      Statements.value(StatementRequest(controller))
  }

  val set4: Handler1 = {
    case Request(inputList, gameState, controller) =>
      controller.setStatementStatus(wrongWall)
      Statements.value(StatementRequest(controller))
  }

  val set: PartialFunction[Request, String] = (set1.andThen(set3).andThen(log)).orElse(set2.andThen(set4).andThen(log))

}
