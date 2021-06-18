package de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent.Instructions

import de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent.GameStates.SelectFigure
import de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent.Statements.selectFigure
import de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent.{InstructionTrait, Request, StatementRequest, Statements}

object IRoll extends InstructionTrait {

  val roll1: Handler0 = {
    case Request(inputList, gameState, controller) if inputList.head != " " =>
      controller.setDicedNumber(controller.rollCube)
      Request(inputList, gameState, controller)
  }

  val roll2: Handler0 = {
    case Request(inputList, gameState, controller) =>
      controller.setPossibleFiguresTrueOrFalse(controller.gameBoard.playersTurn.get.playerNumber)
      Request(inputList, gameState, controller)
  }

  val roll3: Handler1 = {
    case Request(inputList, gameState, controller) =>
      gameState.nextState(SelectFigure(controller))
      controller.setStatementStatus(selectFigure)
      Statements.value(StatementRequest(controller))
  }

  val roll: PartialFunction[Request, String] = roll1.andThen(roll2).andThen(roll3).andThen(log)

}
