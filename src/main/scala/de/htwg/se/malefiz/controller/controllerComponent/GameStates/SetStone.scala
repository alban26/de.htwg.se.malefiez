package de.htwg.se.malefiz.controller.controllerComponent.GameStates

import de.htwg.se.malefiz.controller.controllerComponent.Instructions.ISetStone
import de.htwg.se.malefiz.controller.State
import de.htwg.se.malefiz.controller.controllerComponent.{Controller, Request, State}

case class SetStone(controller: Controller) extends State[GameState] {
  override def handle(string: String, n: GameState): Unit = ISetStone.set(Request(string.split(" ").toList,n,controller))
}
