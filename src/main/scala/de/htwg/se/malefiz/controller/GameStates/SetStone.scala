package de.htwg.se.malefiz.controller.GameStates

import de.htwg.se.malefiz.controller.Instructions.ISetStone
import de.htwg.se.malefiz.controller.{Controller, Request, State}

case class SetStone(controller: Controller) extends State[GameState] {
  override def handle(string: String, n: GameState): Unit = ISetStone.set(Request(string.split(" ").toList,n,controller))
}
