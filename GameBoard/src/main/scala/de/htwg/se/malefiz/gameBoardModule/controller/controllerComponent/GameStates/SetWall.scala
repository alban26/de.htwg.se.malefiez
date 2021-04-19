package de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent.GameStates

import de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent.Instructions.ISetWall
import de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent.{ControllerInterface, Request, State}

case class SetWall(controller: ControllerInterface) extends State[GameState] {

  override def handle(input: String, gameState: GameState): Unit = ISetWall.set(Request(input.split(" ").toList, gameState, controller))
  override def toString: String = "5"

}
