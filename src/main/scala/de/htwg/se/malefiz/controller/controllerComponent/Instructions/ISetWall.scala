package de.htwg.se.malefiz.controller.controllerComponent.Instructions

import de.htwg.se.malefiz.controller.controllerComponent.GameStates.Roll
import de.htwg.se.malefiz.controller.controllerComponent.Statements._
import de.htwg.se.malefiz.controller.controllerComponent.{InstructionTrait, Request, StatementRequest, Statements}

object ISetWall extends InstructionTrait {

  val set1: Handler0 = {
    case Request(x, y, z) if z.getCellList(x.head.toInt).wallPermission && z.getCellList(x.head.toInt).playerNumber == 0 && !z.getCellList(x.head.toInt).hasWall =>
      z.setWall(x.head.toInt)
      Request(x,y,z)
  }

  val set2: Handler0 = {
    case Request(x, y, z) if !z.getCellList(x.head.toInt).wallPermission || z.getCellList(x.head.toInt).playerNumber != 0 || z.getCellList(x.head.toInt).hasWall =>
      Request(x,y,z)
  }

  val set3: Handler1 = {
    case Request(x, y, z) =>
      z.setDicedNumber(0)
      z.setPosisFalse(z.getPlayersTurn.playerNumber)
      z.setPosisCellFalse(z.getPossibleCells.toList)
      z.setPlayersTurn(z.nextPlayer(z.getPlayer,z.getPlayersTurn.playerNumber-1))
      y.nextState(Roll(z))
      z.setStatementStatus(nextPlayer)
      Statements.value(StatementRequest(z))
  }

  val set4: Handler1 = {
    case Request(x, y, z) =>
      z.setStatementStatus(wrongWall)
      Statements.value(StatementRequest(z))
  }

  val set: PartialFunction[Request, String] = (set1 andThen set3 andThen log) orElse (set2 andThen set4 andThen log)

}
