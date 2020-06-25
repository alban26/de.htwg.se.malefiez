package de.htwg.se.malefiz.controller

import de.htwg.se.malefiz.controller.GameStates.GameState

case class Request(list: List[String], n: GameState, c: Controller)
