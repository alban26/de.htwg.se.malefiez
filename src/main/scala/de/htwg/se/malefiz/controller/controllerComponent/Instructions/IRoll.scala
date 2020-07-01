package de.htwg.se.malefiz.controller.controllerComponent.Instructions

import de.htwg.se.malefiz.controller
import de.htwg.se.malefiz.controller.controllerComponent.GameStates.SelectFigure
import de.htwg.se.malefiz.controller.controllerComponent.{InstructionTrait, Request}
import de.htwg.se.malefiz.controller.{InstructionTrait, Request, controllerComponent}

object IRoll extends InstructionTrait{
  val roll1: Handler0 = {
    case Request(x,y,z) if x != ' ' => z.dicedNumber = z.rollCube
      Request(x,y,z)
  }

  val roll2: Handler0 = {
    case Request(x,y,z) => z.setPosisTrue(z.playersTurn.playerNumber)
      controllerComponent.Request(x,y,z)
  }

  val roll3: Handler1 = {
    case Request(x,y,z) => y nextState SelectFigure(z)
      s"Du hast eine ${z.dicedNumber} gewürfelt. Wähle nun deine gewünschte Figur aus."
  }
/*
  val rollErr: Handler1 = {
    case None => "Beim würfeln ist ein Fehler aufgetreten"
  }
*/
  val roll = roll1 andThen roll2 andThen roll3 andThen log //orElse (rollErr andThen log)

}
