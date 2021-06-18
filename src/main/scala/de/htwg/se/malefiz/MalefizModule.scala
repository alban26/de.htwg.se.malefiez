package de.htwg.se.malefiz

import com.google.inject.AbstractModule
import de.htwg.se.malefiz.controller.controllerComponent.ControllerInterface
import net.codingwell.scalaguice.ScalaModule

class MalefizModule extends AbstractModule with ScalaModule {

  override def configure(): Unit = {
    bind[ControllerInterface].to[controller.controllerComponent.controllerBaseImpl.Controller]
  }

}
