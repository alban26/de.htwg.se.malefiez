package de.htwg.se.malefiz.controller.controllerComponent

import de.htwg.se.malefiz.controller.controllerComponent.GameStates.GameState

case class Request(list: List[String], n: GameState, c: ControllerInterface)
case class StatementRequest(c: ControllerInterface)
