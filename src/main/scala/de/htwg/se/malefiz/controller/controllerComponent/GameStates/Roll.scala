package de.htwg.se.malefiz.controller.controllerComponent.GameStates

import de.htwg.se.malefiz.controller.controllerComponent.Instructions.IRoll
import de.htwg.se.malefiz.controller.controllerComponent.{ControllerInterface, Request, State}

case class Roll(controller: ControllerInterface) extends State[GameState] {

  override def handle(input: String, gameState: GameState): Unit = IRoll
    .roll(Request(input.split(" ").toList, gameState, controller))

  override def toString: String = "1"

}
