package de.htwg.se.malefiz.controller.controllerComponent.GameStates

import de.htwg.se.malefiz.controller.controllerComponent.Instructions.ISetFigure
import de.htwg.se.malefiz.controller.controllerComponent.State
import de.htwg.se.malefiz.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.malefiz.controller.controllerComponent.{Request, State}

case class SetFigure(controller: Controller) extends State[GameState] {
  override def handle(string: String, n: GameState): Unit = ISetFigure.set(Request(string.split(" ").toList,n,controller))
}
