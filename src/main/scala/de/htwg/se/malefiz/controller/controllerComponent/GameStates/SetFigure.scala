package de.htwg.se.malefiz.controller.controllerComponent.GameStates

import de.htwg.se.malefiz.controller.controllerComponent.Instructions.ISetFigure
import de.htwg.se.malefiz.controller.controllerComponent.{ControllerInterface, Request, State}

case class SetFigure(controller: ControllerInterface) extends State[GameState] {

  override def handle(input: String, gameState: GameState): Unit = ISetFigure.set(Request(input.split(" ").toList, gameState, controller))
  override def toString: String = "3"

}
