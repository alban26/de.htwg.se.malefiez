package de.htwg.se.malefiz.controller.controllerComponent.Instructions

import de.htwg.se.malefiz.controller.controllerComponent.GameStates.SelectFigure
import de.htwg.se.malefiz.controller.controllerComponent._

object IRoll extends InstructionTrait{
  val roll1: Handler0 = {
    case Request(x,y,z) if x != ' ' => z.dicedNumber = z.rollCube
      Request(x,y,z)
  }

  val roll2: Handler0 = {
    case Request(x,y,z) => z.setPosisTrue(z.playersTurn.playerNumber)
      Request(x,y,z)
  }

  val roll3: Handler1 = {
    case Request(x,y,z) => y nextState SelectFigure(z)
      s"Du hast eine ${z.dicedNumber} gewürfelt. Wähle nun deine gewünschte Figur aus."
  }

  val roll = roll1 andThen roll2 andThen roll3 andThen log

}
