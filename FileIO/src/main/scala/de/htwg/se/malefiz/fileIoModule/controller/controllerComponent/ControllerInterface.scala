package de.htwg.se.malefiz.fileIoModule.controller.controllerComponent

import scala.concurrent.Future

trait ControllerInterface {

  def load(): Unit

  def save(body: String, suffix: String): Unit

}