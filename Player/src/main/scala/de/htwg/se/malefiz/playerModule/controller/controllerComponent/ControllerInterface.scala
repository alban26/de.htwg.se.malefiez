package de.htwg.se.malefiz.playerModule.controller.controllerComponent

import de.htwg.se.malefiz.playerModule.model.playerServiceComponent.Player

trait ControllerInterface {

  def updatePlayerList(playerList: List[String]): Unit

  def rollDice: Option[Int]

  def getPlayerTurn: Option[Player]

}
