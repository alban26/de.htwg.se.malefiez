package de.htwg.se.malefiz.controller.GameStates

import de.htwg.se.malefiz.controller.{Controller, State}

case class Setup(controller: Controller) extends State[GameState] {

  override def handle(string: String, n: GameState): Unit = {
    val input = string.split(" ").toList

    if(controller.gameBoard.players.length == 4 || input.contains("start")) {
      controller.playersTurn = controller.gameBoard.players(0)
      n.nextState(new Roll(controller))
      println("Lieber " + controller.playersTurn + " du bist als erstes dran. Drücke eine beliebige Taste um zu würfeln.")
    } else {
      controller.createPlayer(input(1))
    }
  }
/*
  override def next(n: GameState): Unit = {
    n.nextState(new Playing(controller))
  }
*/
}
