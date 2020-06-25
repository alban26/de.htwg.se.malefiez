package de.htwg.se.malefiz.controller.GameStates

import de.htwg.se.malefiz.controller.{Controller, State}

case class GameState(controller: Controller) {
  var state: State[GameState] = Setup(controller)

  def run(string: String): Unit = {
    state.handle(string, this)
  }

  def nextState(state: State[GameState]): Unit = {
    this.state = state
  }

}
