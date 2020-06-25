package de.htwg.se.malefiz.controller.GameStates

import de.htwg.se.malefiz.controller.Controller

case class Request(list: List[String], n: GameState, c: Controller)
