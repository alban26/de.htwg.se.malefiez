package de.htwg.se.malefiz.playerModule.controller.controllerComponent

import com.google.inject.Inject
import de.htwg.se.malefiz.playerModule.model.playerServiceComponent.Player

trait ControllerInterface {

  def rollCube: Option[Int]

  def getPlayerTurn: Option[Player]

}
