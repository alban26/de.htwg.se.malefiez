package de.htwg.se.malefiz.controller.controllerComponent.Instructions

import de.htwg.se.malefiz.controller.controllerComponent.GameStates.SelectFigure
import de.htwg.se.malefiz.controller.controllerComponent.{InstructionTrait, Request, StatementRequest, Statements}
import de.htwg.se.malefiz.controller.controllerComponent.Statements._

object IRoll extends InstructionTrait{
  val roll1: Handler0 = {
    case Request(x,y,z) if x != ' ' => z.setDicedNumber(z.rollCube)
      Request(x,y,z)
  }

  val roll2: Handler0 = {
    case Request(x,y,z) => z.setPosisTrue(z.getPlayersTurn.playerNumber)
      Request(x,y,z)
  }

  val roll3: Handler1 = {
    case Request(x,y,z) => y nextState SelectFigure(z)
      z.setStatementStatus(selectFigure)
      Statements.value(StatementRequest(z))
  }

  val roll: PartialFunction[Request, String] = roll1 andThen roll2 andThen roll3 andThen log

}
