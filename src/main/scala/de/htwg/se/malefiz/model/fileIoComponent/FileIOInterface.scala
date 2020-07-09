package de.htwg.se.malefiz.model.fileIoComponent

import de.htwg.se.malefiz.controller.controllerComponent.ControllerInterface
import de.htwg.se.malefiz.model.gameBoardComponent.GameboardInterface

trait FileIOInterface {

  def loadController: ControllerInterface
  def load: GameboardInterface
  def save(gameboard: GameboardInterface,controller: ControllerInterface): Unit

}
