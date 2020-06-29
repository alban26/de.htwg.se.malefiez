package de.htwg.se.malefiz.controller.GameStates

import de.htwg.se.malefiz.controller.Instructions.ISetup
import de.htwg.se.malefiz.controller.{Controller, Request, State}

case class Setup(controller: Controller) extends State[GameState] {
  override def handle(string: String, n: GameState): Unit = ISetup.setup(Request(string.split(" ").toList,n,controller))
}
