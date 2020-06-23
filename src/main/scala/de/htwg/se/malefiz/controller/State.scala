package de.htwg.se.malefiz.controller

trait State[T] {
  def handle(string: String, n: T)
}
