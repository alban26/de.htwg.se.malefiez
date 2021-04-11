package de.htwg.se.malefiz.playerModule.controller.controllerComponent.controllerBaseImpl

import com.google.inject.{Guice, Inject, Injector}
import de.htwg.se.malefiz.playerModule.PlayerServerModule
import de.htwg.se.malefiz.playerModule.controller.controllerComponent.ControllerInterface
import de.htwg.se.malefiz.playerModule.model.PlayerServiceInterface
import de.htwg.se.malefiz.playerModule.model.playerServiceComponent.{Player, PlayerService}
import net.codingwell.scalaguice.InjectorExtensions.ScalaInjector

class Controller @Inject() extends ControllerInterface{

  val injector: Injector = Guice.createInjector(new PlayerServerModule)

  val playerService = injector.instance[PlayerServiceInterface]

  override def getPlayerTurn: Option[Player] = playerService.playersTurn

  override def rollCube: Option[Int] = {
    playerService.rollDice
    playerService.dicedNumber
  }
}
