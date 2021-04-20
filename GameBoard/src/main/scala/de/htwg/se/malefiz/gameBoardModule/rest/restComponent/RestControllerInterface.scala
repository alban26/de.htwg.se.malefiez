package de.htwg.se.malefiz.gameBoardModule.rest.restComponent

import de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.malefiz.gameBoardModule.model.gameBoardComponent.GameBoardInterface

trait RestControllerInterface {

  def saveAsJson(gameBoard: GameBoardInterface, controller: Controller): Unit

  def saveAsXML(gameBoard: GameBoardInterface, controller: Controller): Unit

}
