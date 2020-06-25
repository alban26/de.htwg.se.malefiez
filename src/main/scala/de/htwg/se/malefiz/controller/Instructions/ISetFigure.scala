package de.htwg.se.malefiz.controller.Instructions

//import Instructions.{Handler0, Handler1}
import de.htwg.se.malefiz.controller
import de.htwg.se.malefiz.controller.GameStates.Roll
import de.htwg.se.malefiz.controller.{InstructionTrait, Request}

object ISetFigure extends InstructionTrait{
  val set1: Handler0 = {
    case Some(Request(x, y, z)) if x != ' ' =>
      z.setPlayerFigure(z.selectedFigure._1,z.selectedFigure._2,x(0).toInt)
      Some(controller.Request(x,y,z))
  }

  val set2: Handler1 = {
    case Some(Request(x, y, z)) =>
      y.nextState(Roll(z))
      s"Lieber ${z.playersTurn} du bist als nächstes dran. Drücke eine beliebige Taste um zu würfeln."
  }

  val setErr: Handler1 = {
    case None => "Bei Spielerauswahl fehler aufgetreten"
  }

  val set = set1 andThen set2 andThen log orElse (setErr andThen log)
}
