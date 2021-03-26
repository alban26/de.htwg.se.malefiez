package de.htwg.se.malefiz.controller.controllerComponent.Instructions

import de.htwg.se.malefiz.controller.controllerComponent.GameStates.SelectFigure
import de.htwg.se.malefiz.controller.controllerComponent.{InstructionTrait, Request, StatementRequest, Statements}
import de.htwg.se.malefiz.controller.controllerComponent.Statements._

object IRoll extends InstructionTrait {

  val roll1: Handler0 = {
    case Request(inputList, gameState, controller) if inputList.head != " " =>
      controller.setDicedNumber(controller.rollCube.get)
      Request(inputList, gameState, controller)
  }

  val roll2: Handler0 = {
    case Request(inputList, gameState, controller) =>
      //controller.setPossibleFiguresTrue(controller.gameBoard.playersTurn.get.playerNumber)
      controller.setPossibleFiguresTrueOrFalse(controller.gameBoard.playersTurn.get.playerNumber, true)
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
