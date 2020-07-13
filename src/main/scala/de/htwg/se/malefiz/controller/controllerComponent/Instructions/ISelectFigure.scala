package de.htwg.se.malefiz.controller.controllerComponent.Instructions

import de.htwg.se.malefiz.controller.controllerComponent.GameStates.SetFigure
import de.htwg.se.malefiz.controller.controllerComponent.Statements._
import de.htwg.se.malefiz.controller.controllerComponent.{InstructionTrait, Request, StatementRequest, Statements}

object ISelectFigure extends InstructionTrait{

  val select1: Handler0 = {
    case Request(x, y, z) if x.head.toInt == z.getPlayersTurn.playerNumber => z.calculatePath(z.getFigure(x.head.toInt, x(1).toInt), z.getDicedNumber)
      Request(x,y,z)
  }

  val select2: Handler0 = {
    case Request(x, y, z) => z.setSelectedFigures(x.head.toInt, x(1).toInt)
      Request(x,y,z)
  }

  val select3: Handler0 = {
    case Request(x, y, z) =>
      z.setPossibleCellsTrue(z.getPossibleCells.toList)
      Request(x,y,z)
  }

  val select4: Handler1 = {
    case Request(x,y,z) =>
      y nextState SetFigure(z)
      z.setStatementStatus(selectField)
      Statements.value(StatementRequest(z))
  }

  /*wenn nicht eigener Spieler ausgewählt wird*/
  val select5: Handler0 = {
    case Request(x, y, z) if x.head.toInt != z.getPlayersTurn.playerNumber =>
      Request(x,y,z)
  }

  val select6: Handler1 = {
    case Request(x, y, z) =>
      z.setStatementStatus(selectWrongFigure)
      Statements.value(StatementRequest(z))
  }

  val select: PartialFunction[Request, String] = (select1 andThen select2 andThen select3 andThen select4 andThen log) orElse (select5 andThen select6 andThen log)

}
