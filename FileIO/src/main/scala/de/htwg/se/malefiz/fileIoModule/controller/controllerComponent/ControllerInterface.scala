package de.htwg.se.malefiz.fileIoModule.controller.controllerComponent

trait ControllerInterface {

  def load(): Unit

  def save(body: String, suffix: String): Unit

}