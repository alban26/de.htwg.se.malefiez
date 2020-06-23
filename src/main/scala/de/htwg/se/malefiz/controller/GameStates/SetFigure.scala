package de.htwg.se.malefiz.controller.GameStates

import de.htwg.se.malefiz.controller.{Controller, State}

class SetFigure(controller: Controller) extends State[GameState] {
  override def handle(string: String, n: GameState): Unit = {
    val cellNumber = string.toInt
    controller.setPlayerFigure(controller.selectedFigure._1,controller.selectedFigure._2,cellNumber)
    n.nextState(new Roll(controller))
    println("Lieber " + controller.playersTurn + " du bist als nächstes dran. Drücke eine beliebige Taste um zu würfeln.")
  }
}
