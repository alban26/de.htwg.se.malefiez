package de.htwg.se.malefiz.controller.controllerComponent.GameStates

import de.htwg.se.malefiz.controller.controllerComponent.Instructions.ISetup
import de.htwg.se.malefiz.controller.controllerComponent.{ControllerInterface, Request, State}

case class Setup(controller: ControllerInterface) extends State[GameState] {

  override def handle(string: String, n: GameState): Unit = ISetup.setup(Request(string.split(" ").toList,n,controller))
  override def toString: String = "4"

}
