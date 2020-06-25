package de.htwg.se.malefiz.controller.GameStates

import de.htwg.se.malefiz.controller.Instructions.IRoll
import de.htwg.se.malefiz.controller.{Controller, Request, State}

case class Roll(controller: Controller) extends State[GameState] {
  override def handle(string: String, n: GameState): Unit = IRoll.roll(Some(Request(string.split(" ").toList,n,controller)))
}
