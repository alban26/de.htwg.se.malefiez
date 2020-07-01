package de.htwg.se.malefiz.controller.controllerComponent

import de.htwg.se.malefiz.controller.controllerComponent.GameStates.GameState
import de.htwg.se.malefiz.controller.controllerComponent.controllerBaseImpl.Controller

case class Request(list: List[String], n: GameState, c: Controller)
