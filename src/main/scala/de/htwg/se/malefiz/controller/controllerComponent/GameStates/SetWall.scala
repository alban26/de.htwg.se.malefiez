package de.htwg.se.malefiz.controller.controllerComponent.GameStates

import de.htwg.se.malefiz.controller.controllerComponent.Instructions.ISetWall
import de.htwg.se.malefiz.controller.controllerComponent.{ControllerInterface, Request, State}

case class SetWall(controller: ControllerInterface) extends State[GameState] {

  override def handle(string: String, n: GameState): Unit = ISetWall.set(Request(string.split(" ").toList,n,controller))
  override def toString: String = "5"

}
