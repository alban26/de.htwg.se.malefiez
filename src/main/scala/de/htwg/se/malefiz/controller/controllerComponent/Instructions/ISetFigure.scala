package de.htwg.se.malefiz.controller.controllerComponent.Instructions


import de.htwg.se.malefiz.controller.controllerComponent.GameStates.{Roll, SetWall, Setup}
import de.htwg.se.malefiz.controller.controllerComponent.{InstructionTrait, Request, StatementRequest, Statements}
import de.htwg.se.malefiz.controller.controllerComponent.Statements._

object ISetFigure extends InstructionTrait{

  val set1: Handler0 = {
    case Request(x, y, z) if !z.getCellList(x.head.toInt).hasWall && (x.head.toInt != 131) && z.getPossibleCells.contains(x.head.toInt) && z.getCellList(x.head.toInt).playerNumber != z.getPlayersTurn.playerNumber =>
      z.setPlayerFigure(z.getSelectedFigure._1,z.getSelectedFigure._2,x.head.toInt)
      Request(x,y,z)
  }



  val set2: Handler0 = {
    case Request(x, y, z) if z.getCellList(x.head.toInt).hasWall && z.getPossibleCells.contains(x.head.toInt) =>

      z.setPlayerFigure(z.getSelectedFigure._1,z.getSelectedFigure._2,x.head.toInt)
      Request(x,y,z)
  }

  val set3: Handler1 = {
    case Request(x, y, z) =>
      z.setDicedNumber(0)
      z.setPosisFalse(z.getPlayersTurn.playerNumber)
      z.setPosisCellFalse(z.getPossibleCells.toList)
      z.resetPossibleCells()
      z.setPlayersTurn(z.nextPlayer(z.getPlayer,z.getPlayersTurn.playerNumber-1))
      y.nextState(Roll(z))
      z.setStatementStatus(nextPlayer)
      Statements.value(StatementRequest(z))
  }

  val set4: Handler1 = {
    case Request(x, y, z) =>
      y.nextState(SetWall(z))
      z.setStatementStatus(wall)
      z.setPosisFalse(z.getPlayersTurn.playerNumber)
      z.setPosisCellFalse(z.getPossibleCells.toList)
      z.resetPossibleCells()
      Statements.value(StatementRequest(z))
  }
  val set5: Handler0 = {
    case Request(x, y, z) if x.head.toInt == 131 =>
      Request(x, y, z)
  }

  val set6: Handler1 = {
    case Request(x, y, z) =>
      y.nextState(Setup(z))
      z.setStatementStatus(won)
      z.weHaveAWinner()
      Statements.value(StatementRequest(z))
  }

  val set7: Handler0 = {
    case Request(x, y, z) if !z.getPossibleCells.contains(x.head.toInt) =>
      Request(x,y,z)
  }

  val set8: Handler1 = {
    case Request(x, y, z) =>
      z.setStatementStatus(wrongField)
      Statements.value(StatementRequest(z))
  }

  val set: PartialFunction[Request, String] = (set1 andThen set3 andThen log) orElse (set2 andThen set4 andThen log) orElse
    (set5 andThen set6 andThen log) orElse (set7 andThen set8 andThen log)

}
