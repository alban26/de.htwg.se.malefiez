package de.htwg.se.malefiz.controller.controllerComponent.GameStates

import de.htwg.se.malefiz.controller.controllerComponent.{ControllerInterface, State}

case class GameState (controller: ControllerInterface) {

  var currentState: State[GameState] = Setup(controller)

  def run(input: String): Unit = {
    currentState.handle(input, this)
  }

  def nextState(state: State[GameState]): Unit = {
    this.currentState = state
  }

}
