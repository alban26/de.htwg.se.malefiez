package de.htwg.se.malefiz


import com.google.inject.AbstractModule
import com.google.inject.name.Names
import net.codingwell.scalaguice.ScalaModule
import de.htwg.se.malefiz.controller.controllerComponent._
import de.htwg.se.malefiz.model.gameBoardComponent.GameboardInterface
import de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl.GameBoard


class MalefizModule extends AbstractModule with ScalaModule{

  override def configure(): Unit = {

    bind[GameboardInterface].to[GameBoard]
    bind[ControllerInterface].to[controllerBaseImpl.Controller]
    bind[GameboardInterface].toInstance(GameBoard())

  }



}
