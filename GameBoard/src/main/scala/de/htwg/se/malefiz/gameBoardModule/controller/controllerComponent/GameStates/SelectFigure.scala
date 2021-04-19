package de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent.GameStates

import de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent.Instructions.ISelectFigure
import de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent.{ControllerInterface, Request, State}

case class SelectFigure(controller: ControllerInterface) extends State[GameState] {

  override def handle(input: String, gameState: GameState): Unit =
    ISelectFigure.select(Request(input.split(" ").toList, gameState, controller))
  override def toString: String = "2"

}
