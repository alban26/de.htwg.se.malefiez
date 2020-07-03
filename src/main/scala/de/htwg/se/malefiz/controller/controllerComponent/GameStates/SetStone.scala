package de.htwg.se.malefiz.controller.controllerComponent.GameStates

import de.htwg.se.malefiz.controller.controllerComponent.Instructions.ISetStone
import de.htwg.se.malefiz.controller.controllerComponent.{ControllerInterface, Request, State}
import de.htwg.se.malefiz.controller.controllerComponent.controllerBaseImpl.Controller

case class SetStone(controller: ControllerInterface) extends State[GameState] {
  override def handle(string: String, n: GameState): Unit = ISetStone.set(Request(string.split(" ").toList,n,controller))
}
