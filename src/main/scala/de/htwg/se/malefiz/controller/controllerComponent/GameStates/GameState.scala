package de.htwg.se.malefiz.controller.controllerComponent.GameStates

import de.htwg.se.malefiz.controller.controllerComponent.{ControllerInterface, State}

case class GameState (controller: ControllerInterface) {

  var state: State[GameState] = Setup(controller)

  def run(input: String): Unit = {
    state.handle(input, this)
  }

  def nextState(state: State[GameState]): Unit = {
    this.state = state
  }

}
