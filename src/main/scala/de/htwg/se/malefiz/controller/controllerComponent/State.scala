package de.htwg.se.malefiz.controller.controllerComponent

trait State[T] {

  def handle(string: String, n: T): Unit

}
