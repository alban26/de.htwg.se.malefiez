package de.htwg.se.malefiz.controller.controllerComponent.Instructions

import de.htwg.se.malefiz.controller.controllerComponent.Statements._
import de.htwg.se.malefiz.controller.controllerComponent.GameStates.Roll
import de.htwg.se.malefiz.controller.controllerComponent.{InstructionTrait, Request, StatementRequest, Statements}

object ISetup extends InstructionTrait {

  val setup1: Handler0 = {
    case Request(inputList, gameState, controller)
        if inputList.contains("start") ||
          controller.gameBoard.players.length == 4 =>
      controller.setPlayersTurn(controller.gameBoard.players.head)
      Request(inputList, gameState, controller)
  }

  val setup2: Handler1 = {
    case Request(inputList, gameState, controller) =>
      gameState.nextState(Roll(controller))
      controller.setStatementStatus(roll)
      Statements.value(StatementRequest(controller))
  }

  val setup3: Handler1 = {
    case Request(inputList, gameState, controller) =>
      controller.createPlayer(inputList(1))
      controller.setStatementStatus(addPlayer)
      Statements.value(StatementRequest(controller))
  }

  val setup: PartialFunction[Request, String] = setup1.andThen(setup2).orElse(setup3).andThen(log)

}
