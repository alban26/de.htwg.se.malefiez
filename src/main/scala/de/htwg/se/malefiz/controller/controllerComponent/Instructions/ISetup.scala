package de.htwg.se.malefiz.controller.controllerComponent.Instructions
import de.htwg.se.malefiz.controller.controllerComponent.Statements._
import de.htwg.se.malefiz.controller.controllerComponent.GameStates.Roll
import de.htwg.se.malefiz.controller.controllerComponent.{InstructionTrait, Request, StatementRequest, Statements}

object ISetup extends InstructionTrait {

  val setup1: Handler0 = {
    case Request(x,y,z) if x.contains("start") || z.getPlayer.length == 4 => z.setPlayersTurn(z.getPlayer.head)
      Request(x,y,z)
  }

  val setup2: Handler1 = {
    case Request(x,y,z) => y.nextState(Roll(z))
      z.setStatementStatus(roll)
      Statements.value(StatementRequest(z))
  }

  val setup3: Handler1 = {
    case Request(x, y, z) => z.createPlayer(x(1))
      z.setStatementStatus(addPlayer)
      Statements.value(StatementRequest(z))
  }

  val setup: PartialFunction[Request, String] = setup1 andThen setup2 orElse setup3 andThen log
}
