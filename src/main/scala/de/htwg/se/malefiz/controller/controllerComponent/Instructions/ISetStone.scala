package de.htwg.se.malefiz.controller.controllerComponent.Instructions


import de.htwg.se.malefiz.controller.controllerComponent.GameStates.Roll
import de.htwg.se.malefiz.controller.controllerComponent
import de.htwg.se.malefiz.controller.controllerComponent.{InstructionTrait, Request, Statements}

object ISetStone extends InstructionTrait {

  val set1: Handler0 = {
    case Request(x, y, z) if z.getCellList(x.head.toInt).wallPermission && z.getCellList(x.head.toInt).playerNumber == 0 =>
      z.setWall(x.head.toInt)
      Request(x,y,z)
  }

  val set2: Handler0 = {
    case Request(x, y, z) if !z.getCellList(x.head.toInt).wallPermission || z.getCellList(x.head.toInt).playerNumber != 0 =>
      controllerComponent.Request(x,y,z)
  }

  val set3: Handler1 = {
    case Request(x, y, z) =>
      z.dicedNumber = 0
      z.setPosisFalse(z.playersTurn.playerNumber)
      z.setPosisCellFalse(z.getPossibleCells.toList)
      z.playersTurn = z.gameBoard.nextPlayer(z.getPlayer,z.playersTurn.playerNumber-1)
      y.nextState(Roll(z))
      z.statementStatus = Statements.nextPlayer
      Statements.message(z.statementStatus).substring(0,7) + z.playersTurn + Statements.message(z.statementStatus).substring(6)
  }

  val set4: Handler1 = {
    case Request(x, y, z) =>
      z.statementStatus = Statements.wrongWall
      Statements.message(z.statementStatus).substring(0,7) + z.playersTurn + Statements.message(z.statementStatus).substring(6)
      //s"Lieber ${z.playersTurn} du darfst die Mauer dort nicht setzen. Bitte wähle ein anderes Feld aus."
  }

  val set = (set1 andThen set3 andThen log) orElse (set2 andThen set4 andThen log)

}
