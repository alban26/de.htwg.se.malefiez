package de.htwg.se.malefiz.playerModule.controller.controllerComponent.controllerBaseImpl

import com.google.inject.{Guice, Inject, Injector}
import de.htwg.se.malefiz.playerModule.PlayerServerModule
import de.htwg.se.malefiz.playerModule.controller.controllerComponent.ControllerInterface
import de.htwg.se.malefiz.playerModule.model.PlayerServiceInterface
import de.htwg.se.malefiz.playerModule.model.playerServiceComponent.Player

class Controller @Inject() (var playerService: PlayerServiceInterface) extends ControllerInterface{

  val injector: Injector = Guice.createInjector(new PlayerServerModule)
  //val playerService: PlayerServiceInterface = injector.instance[PlayerServiceInterface]

  override def getPlayerTurn: Option[Player] = playerService.playersTurn

  override def rollDice: Option[Int] = {
    playerService = playerService.rollDice
    playerService.dicedNumber
  }

  override def updatePlayerList(playerList: List[String]): Unit =  {
    playerService = playerService.updatePlayerList(playerList)
  }
}
