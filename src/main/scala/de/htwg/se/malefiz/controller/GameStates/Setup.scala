package de.htwg.se.malefiz.controller.GameStates

import de.htwg.se.malefiz.controller.{Controller, State}

case class Setup(controller: Controller) extends State[GameState] {
  override def handle(string: String, n: GameState): Unit = Instructions.setup(Request(string.split(" ").toList,n,controller))
}
