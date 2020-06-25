package de.htwg.se.malefiz.controller.GameStates

import de.htwg.se.malefiz.controller.Instructions.ISetFigure
import de.htwg.se.malefiz.controller.{Controller, Request, State}

case class SetFigure(controller: Controller) extends State[GameState] {
  override def handle(string: String, n: GameState): Unit = ISetFigure.set(Some(Request(string.split(" ").toList,n,controller)))
}
