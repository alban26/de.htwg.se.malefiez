package de.htwg.se.malefiz

import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule
import de.htwg.se.malefiz.controller.controllerComponent.ControllerInterface
import de.htwg.se.malefiz.model.fileIoComponent.FileIOInterface
import de.htwg.se.malefiz.model.gameBoardComponent.{CreatorInterface, GameBoardInterface}

class MalefizModule extends AbstractModule with ScalaModule{

  override def configure(): Unit = {

    bind[ControllerInterface].to[controller.controllerComponent.controllerBaseImpl.Controller]
    bind[GameBoardInterface].to[model.gameBoardComponent.gameBoardBaseImpl.GameBoard]
    bind[CreatorInterface].to[model.gameBoardComponent.gameBoardBaseImpl.Creator]
    bind[FileIOInterface].to[model.fileIoComponent.fileIoJsonImpl.FileIO]
    //bind[FileIOInterface].to[model.fileIoComponent.fileIoXmlImpl.FileIO]
  }

}
