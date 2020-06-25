package de.htwg.se.malefiz.controller.GameStates

import de.htwg.se.malefiz.controller.{Controller, State}

case class Roll(controller: Controller) extends State[GameState] {
  override def handle(string: String, n: GameState): Unit = {
    string match {
      case _ => controller.dicedNumber = controller.rollCube
        controller.setPosisTrue (controller.playersTurn.playerNumber)
        println (controller.playersTurn + ", du hast eine " + controller.dicedNumber + " gewürfelt. Wähle nun deine Figur aus. ")
        n.nextState (SelectFigure(controller))
    }
  }
/*
  override def next(n: GameState): Unit = {
    n.nextState(new Setup(controller))
  }
*/
}
