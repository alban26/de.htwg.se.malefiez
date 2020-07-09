package de.htwg.se.malefiz.controller.controllerComponent.GameStates

import de.htwg.se.malefiz.controller.controllerComponent.Instructions.ISelectFigure
import de.htwg.se.malefiz.controller.controllerComponent.{ControllerInterface, Request, State}

case class SelectFigure(controller: ControllerInterface) extends State[GameState] {

  override def handle(string: String, n: GameState): Unit = ISelectFigure.select(Request(string.split(" ").toList,n,controller))
  override def toString: String = "2"

}
