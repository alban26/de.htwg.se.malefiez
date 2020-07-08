package de.htwg.se.malefiz.controller.controllerComponent.GameStates

import de.htwg.se.malefiz.controller.controllerComponent.Instructions.IRoll
import de.htwg.se.malefiz.controller.controllerComponent.{ControllerInterface, Request, State}
import de.htwg.se.malefiz.controller.controllerComponent.controllerBaseImpl.Controller

case class Roll(controller: ControllerInterface) extends State[GameState] {
  override def handle(string: String, n: GameState): Unit = IRoll.roll(Request(string.split(" ").toList,n,controller))

  override def toString: String = "1"
}
