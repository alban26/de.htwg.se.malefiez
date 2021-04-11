package de.htwg.se.malefiz.playerModule

import com.google.inject.AbstractModule
import de.htwg.se.malefiz.playerModule.controller.controllerComponent.ControllerInterface
import de.htwg.se.malefiz.playerModule.model.PlayerServiceInterface
import net.codingwell.scalaguice.ScalaModule

class PlayerServerModule extends AbstractModule with ScalaModule{

  override def configure(): Unit = {

    bind[ControllerInterface].to[controller.controllerComponent.controllerBaseImpl.Controller]
    bind[PlayerServiceInterface].to[model.playerServiceComponent.PlayerService]

  }

}
