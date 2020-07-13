package de.htwg.se.malefiz.model.fileIoComponent

import de.htwg.se.malefiz.controller.controllerComponent.ControllerInterface
import de.htwg.se.malefiz.model.gameBoardComponent.GameBoardInterface

trait FileIOInterface {

  def loadController: ControllerInterface
  def load: GameBoardInterface
  def save(gameBoard: GameBoardInterface,controller: ControllerInterface): Unit

}
