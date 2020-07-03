package de.htwg.se.malefiz.controller.controllerComponent.Instructions

import de.htwg.se.malefiz.controller.controllerComponent.GameStates.SetFigure
import de.htwg.se.malefiz.controller.controllerComponent.Instructions.ISetStone.Handler1
import de.htwg.se.malefiz.controller.controllerComponent.{InstructionTrait, Request, Statements}

object ISelectFigure extends InstructionTrait{
  val select1: Handler0 = {
    case Request(x, y, z) if x.head.toInt == z.playersTurn.playerNumber => z.getPCells(z.getFigure(x.head.toInt, x(1).toInt), z.dicedNumber)
      Request(x,y,z)
  }

  val select2: Handler0 = {
    case Request(x, y, z) => z.selectedFigure = (x.head.toInt, x(1).toInt)
      Request(x,y,z)
  }

  val select3: Handler0 = {
    case Request(x, y, z) =>
      z.setPosisCellTrue(z.getPossibleCells.toList)
      Request(x,y,z)
  }

  val select4: Handler1 = {
    case Request(x,y,z) =>
      y nextState SetFigure(z)
      z.statementStatus = Statements.selectField
      Statements.message(z.statementStatus)
      //"Du kannst nun auf folgende Felder gehen. Wähle eine aus indem du die Nummer eintippst."
  }

  /*wenn nicht eigener Spieler ausgewählt wird*/
  val select5: Handler0 = {
    case Request(x, y, z) if x.head.toInt != z.playersTurn.playerNumber =>
      Request(x,y,z)
  }

  val select6: Handler1 = {
    case Request(x, y, z) =>
      z.statementStatus = Statements.selectWrongFigure
      Statements.message(z.statementStatus).substring(0,7) + z.playersTurn + Statements.message(z.statementStatus).substring(6)
  }


  val select: PartialFunction[Request, String] = (select1 andThen select2 andThen select3 andThen select4 andThen log) orElse (select5 andThen select6 andThen log)
}
