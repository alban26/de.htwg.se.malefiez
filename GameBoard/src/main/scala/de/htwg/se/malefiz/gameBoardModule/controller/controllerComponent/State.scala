package de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent

trait State[T] {

  def handle(string: String, n: T): Unit

}
