package de.htwg.se.malefiz.fileIoModule

import com.google.inject.AbstractModule
import de.htwg.se.malefiz.fileIoModule.controller.controllerComponent.ControllerInterface
import de.htwg.se.malefiz.fileIoModule.model.FileIOInterface
import net.codingwell.scalaguice.ScalaModule

class FileIOServerModule extends AbstractModule with ScalaModule {

  override def configure(): Unit = {
    bind[ControllerInterface].to[controller.controllerComponent.controllerBaseImpl.Controller]
    bind[FileIOInterface].to[model.fileIOComponent.fileIoJsonImpl.FileIO]

    //bind[FileIOInterface].to[model.fileIoComponent.fileIoXmlImpl.FileIO]
  }

}
