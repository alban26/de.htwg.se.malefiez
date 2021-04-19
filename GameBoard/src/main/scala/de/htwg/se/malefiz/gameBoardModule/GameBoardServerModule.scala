package de.htwg.se.malefiz.gameBoardModule

import com.google.inject.AbstractModule
import de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent.ControllerInterface
import de.htwg.se.malefiz.gameBoardModule.model.gameBoardComponent.{CreatorInterface, GameBoardInterface}
import net.codingwell.scalaguice.ScalaModule

class GameBoardServerModule extends AbstractModule with ScalaModule{

  override def configure(): Unit = {

    bind[ControllerInterface].to[controller.controllerComponent.controllerBaseImpl.Controller]
    bind[GameBoardInterface].to[model.gameBoardComponent.gameBoardBaseImpl.GameBoard]
    bind[CreatorInterface].to[model.gameBoardComponent.gameBoardBaseImpl.Creator]
    //bind[FileIOInterface].to[model.fileIoComponent.fileIoJsonImpl.FileIO]
    //bind[FileIOInterface].to[model.fileIoComponent.fileIoXmlImpl.FileIO]
  }

}