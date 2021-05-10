package de.htwg.se.malefiz.gameBoardModule.model.dbComponent

import de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent.ControllerInterface
import de.htwg.se.malefiz.gameBoardModule.model.gameBoardComponent.GameBoardInterface

import scala.concurrent.Future

trait DaoInterface {

  def create(gameBoardInterface: GameBoardInterface, controllerInterface: ControllerInterface): Unit

  def read(): GameBoardInterface

  def update()

  def delete()

}
