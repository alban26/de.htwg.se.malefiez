package de.htwg.se.malefiz

import com.google.inject.AbstractModule
import de.htwg.se.malefiz.Malefiz.{cellConfigFile, cellLinksFile}
import net.codingwell.scalaguice.ScalaModule
import de.htwg.se.malefiz.controller.controllerComponent.ControllerInterface
import de.htwg.se.malefiz.model.fileIoComponent.FileIOInterface
import de.htwg.se.malefiz.model.gameBoardComponent.GameboardInterface
import de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl.{Creator, GameBoard}

class MalefizModule extends AbstractModule with ScalaModule{

  override def configure(): Unit = {

    bind[ControllerInterface].to[controller.controllerComponent.controllerBaseImpl.Controller]
    bind[GameboardInterface].to[model.gameBoardComponent.gameBoardBaseImpl.GameBoard]

    /*bind[GameboardInterface].toInstance(GameBoard(
      Creator().getCellList(cellConfigFile),
      List().empty,
      Creator().getCellGraph(cellLinksFile),
      Set().empty
    ))*/

    bind[FileIOInterface].to[model.fileIoComponent.fileIoJsonImpl.FileIO]
    //bind[FileIOInterface].to[model.fileIoComponent.fileIoJsonImpl.FileIO]
  }

}
