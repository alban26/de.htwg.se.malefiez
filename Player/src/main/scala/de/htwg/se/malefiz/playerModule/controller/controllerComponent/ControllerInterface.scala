package de.htwg.se.malefiz.playerModule.controller.controllerComponent

import de.htwg.se.malefiz.playerModule.model.playerServiceComponent.Player
import spray.json.JsValue

trait ControllerInterface {

  def updatePlayerList(playerList: List[String]): JsValue

  def rollDice: Option[Int]

  def getPlayerTurn: Option[Player]

  def getPlayerName(playerNumber: Int) : String

  def getNumberOfPlayers : Int

}
