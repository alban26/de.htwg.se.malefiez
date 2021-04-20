package de.htwg.se.malefiz.fileIoModule.controller.controllerComponent

trait ControllerInterface {

  def load(): String

  def save(s: String): Unit

}