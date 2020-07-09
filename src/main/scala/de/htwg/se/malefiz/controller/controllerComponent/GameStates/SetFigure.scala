package de.htwg.se.malefiz.controller.controllerComponent.GameStates

import de.htwg.se.malefiz.controller.controllerComponent.Instructions.ISetFigure
import de.htwg.se.malefiz.controller.controllerComponent.{ControllerInterface, Request, State}

case class SetFigure(controller: ControllerInterface) extends State[GameState] {

  override def handle(string: String, n: GameState): Unit = ISetFigure.set(Request(string.split(" ").toList,n,controller))
  override def toString: String = "3"

}
