package de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent

import de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent.GameStates.GameState

case class Request(inputList: List[String], gameState: GameState, controller: ControllerInterface)
case class StatementRequest(controller: ControllerInterface)