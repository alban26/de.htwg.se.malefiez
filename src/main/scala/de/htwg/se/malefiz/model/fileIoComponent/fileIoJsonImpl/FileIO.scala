package de.htwg.se.malefiz.model.fileIoComponent.fileIoJsonImpl

import de.htwg.se.malefiz.controller.controllerComponent.ControllerInterface
import de.htwg.se.malefiz.model.fileIoComponent.FileIOInterface
import de.htwg.se.malefiz.model.gameBoardComponent.GameboardInterface

class FileIO extends FileIOInterface{
  override def load: GameboardInterface = ???

  override def save(gameboard: GameboardInterface, controller: ControllerInterface): Unit = ???

  override def loadController: ControllerInterface = ???
}
