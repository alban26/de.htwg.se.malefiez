package de.htwg.se.malefiz.playerModule.controller.controllerComponent.controllerBaseImpl

import com.google.inject.{Guice, Inject, Injector}
import de.htwg.se.malefiz.playerModule.{JsonSupport, PlayerServerModule}
import de.htwg.se.malefiz.playerModule.controller.controllerComponent.ControllerInterface
import de.htwg.se.malefiz.playerModule.model.PlayerServiceInterface
import de.htwg.se.malefiz.playerModule.model.playerServiceComponent.Player
import spray.json.{JsValue, enrichAny}

class Controller @Inject() (var playerService: PlayerServiceInterface) extends ControllerInterface with JsonSupport{

  val injector: Injector = Guice.createInjector(new PlayerServerModule)
  //val playerService: PlayerServiceInterface = injector.instance[PlayerServiceInterface]

  override def getPlayerTurn: Option[Player] = playerService.playersTurn

  override def rollDice: Option[Int] = {
    playerService = playerService.rollDice
    playerService.dicedNumber
  }

  override def updatePlayerList(playerList: List[String]): JsValue =  {
    playerService = playerService.updatePlayerList(playerList)
    playerService = playerService.updatePlayersTurn(playerService.playerList.head)
    playerService.playerList.toJson
  }

  override def getPlayerName(playerNumber: Int): String =
    playerService.playerList(playerNumber).getOrElse("-").toString

  override def getNumberOfPlayers: Int = playerService.playerList.length
}
