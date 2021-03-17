package de.htwg.se.malefiz.controller.controllerComponent.Instructions

import de.htwg.se.malefiz.controller.controllerComponent.GameStates.{Roll, SelectFigure, SetWall, Setup}
import de.htwg.se.malefiz.controller.controllerComponent.Instructions.ISelectFigure.Handler0
import de.htwg.se.malefiz.controller.controllerComponent.Statements._
import de.htwg.se.malefiz.controller.controllerComponent.{InstructionTrait, Request, StatementRequest, Statements}

object ISetFigure extends InstructionTrait{

  val set1: Handler0 = {
    case Request(inputList, gameState, controller) if !controller.getCellList(inputList.head.toInt).hasWall && (inputList.head.toInt != 131) && controller.getPossibleCells.contains(inputList.head.toInt) && controller.getCellList(inputList.head.toInt).playerNumber != controller.getPlayersTurn.playerNumber =>
      controller.setPlayerFigure(controller.getSelectedFigure._1,controller.getSelectedFigure._2,inputList.head.toInt)
      Request(inputList,gameState,controller)
  }

  val select1: Handler1 = {
    case Request(inputList, gameState, controller) if inputList.length == 2 && inputList.head.toInt == controller.getSelectedFigure._1 && inputList(1).toInt == controller.getSelectedFigure._2 =>
      controller.setPossibleCellsFalse(controller.getPossibleCells.toList)

      controller.resetPossibleCells()
      gameState nextState SelectFigure(controller)
      controller.setStatementStatus(changeFigure)
      Statements.value(StatementRequest(controller))
  }

  val set2: Handler0 = {
    case Request(inputList, gameState, controller) if controller.getCellList(inputList.head.toInt).hasWall && controller.getPossibleCells.contains(inputList.head.toInt) =>
      controller.setPlayerFigure(controller.getSelectedFigure._1,controller.getSelectedFigure._2,inputList.head.toInt)
      Request(inputList,gameState,controller)
  }

  val set3: Handler1 = {
    case Request(inputList, gameState, controller) =>
      controller.setDicedNumber(0)
      controller.setPossibleFiguresFalse(controller.getPlayersTurn.playerNumber)
      controller.setPossibleCellsFalse(controller.getPossibleCells.toList)
      controller.resetPossibleCells()
      controller.setPlayersTurn(controller.nextPlayer(controller.getPlayer,controller.getPlayersTurn.playerNumber-1))
      gameState.nextState(Roll(controller))
      controller.setStatementStatus(nextPlayer)
      Statements.value(StatementRequest(controller))
  }

  val set4: Handler1 = {
    case Request(inputList, gameState, controller) =>
      gameState.nextState(SetWall(controller))
      controller.setStatementStatus(wall)
      controller.setPossibleFiguresFalse(controller.getPlayersTurn.playerNumber)
      controller.setPossibleCellsFalse(controller.getPossibleCells.toList)
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
    case Request(inputList, gameState, controller) if !controller.getPossibleCells.contains(inputList.head.toInt) || controller.getCellList(inputList.head.toInt).playerNumber == controller.getPlayersTurn.playerNumber =>
      Request(inputList, gameState, controller)
  }

  val set8: Handler1 = {
    case Request(inputList, gameState, controller) =>
      controller.setStatementStatus(wrongField)
      Statements.value(StatementRequest(controller))
  }


  val set: PartialFunction[Request, String] =  (set1 andThen set3 andThen log) orElse (select1 andThen log ) orElse(set2 andThen set4 andThen log) orElse
    (set5 andThen set6 andThen log) orElse (set7 andThen set8 andThen log)

}
