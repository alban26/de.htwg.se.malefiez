package de.htwg.se.malefiz


import com.google.inject.AbstractModule
import com.google.inject.name.Names
import de.htwg.se.malefiz.Malefiz.{cellConfigFile, cellLinksFile}
import net.codingwell.scalaguice.ScalaModule
import de.htwg.se.malefiz.controller.controllerComponent.{ControllerInterface, _}
import de.htwg.se.malefiz.model.gameBoardComponent.{CreatorInterface, GameboardInterface}
import de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl.{Cell, Creator, GameBoard}
import de.htwg.se.malefiz.model.playerComponent.Player

import scala.collection.{immutable, mutable}
import scala.collection.mutable.Map

class MalefizModule extends AbstractModule with ScalaModule{

  override def configure(): Unit = {

    bind[ControllerInterface].to[controller.controllerComponent.controllerBaseImpl.Controller]

    bind[GameboardInterface].toInstance(GameBoard(
      Creator().getCellList(cellConfigFile),
      List().empty,
      Creator().getCellGraph(cellLinksFile),
      Set().empty
    ))

  }
}
