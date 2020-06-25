package de.htwg.se.malefiz.controller.GameStates

import de.htwg.se.malefiz.controller.Instructions.ISelectFigure
import de.htwg.se.malefiz.controller.{Controller, Request, State}

case class SelectFigure(controller: Controller) extends State[GameState] {
  override def handle(string: String, n: GameState): Unit = ISelectFigure.select(Some(Request(string.split(" ").toList,n,controller)))
}
