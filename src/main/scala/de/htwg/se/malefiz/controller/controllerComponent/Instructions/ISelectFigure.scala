package de.htwg.se.malefiz.controller.controllerComponent.Instructions

import de.htwg.se.malefiz.controller.controllerComponent.GameStates.SetFigure
import de.htwg.se.malefiz.controller.controllerComponent.{InstructionTrait, Request}

object ISelectFigure extends InstructionTrait{
  val select1: Handler0 = {
    case Request(x, y, z) if x.length == 2 => z.getPCells(z.getFigure(x(0).toInt, x(1).toInt), z.dicedNumber)
      Request(x,y,z)
  }

  val select2: Handler0 = {
    case Request(x, y, z) => z.selectedFigure = (x(0).toInt, x(1).toInt)
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
      "Du kannst nun auf folgende Felder gehen. Wähle eine aus indem du die Nummer eintippst."
  }

  /*
  val selectErr: Handler1 = {
    case None => "Bei Spielerauswahl fehler aufgetreten"
  }
*/
  val select = select1 andThen select2 andThen select3 andThen select4 andThen log // orElse (selectErr andThen log)
}
