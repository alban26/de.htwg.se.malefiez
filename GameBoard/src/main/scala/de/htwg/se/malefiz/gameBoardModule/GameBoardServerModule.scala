package de.htwg.se.malefiz.gameBoardModule

import com.google.inject.AbstractModule
import de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent.ControllerInterface
import de.htwg.se.malefiz.gameBoardModule.model.dbComponent.DaoInterface
import de.htwg.se.malefiz.gameBoardModule.model.dbComponent.mongoDbImpl._
import de.htwg.se.malefiz.gameBoardModule.model.dbComponent.slickImpl.DaoSlick
import de.htwg.se.malefiz.gameBoardModule.model.gameBoardComponent.{CreatorInterface, GameBoardInterface}
import de.htwg.se.malefiz.gameBoardModule.rest.restComponent.RestControllerInterface
import net.codingwell.scalaguice.ScalaModule

class GameBoardServerModule extends AbstractModule with ScalaModule {

  override def configure(): Unit = {
    bind[ControllerInterface].to[controller.controllerComponent.controllerBaseImpl.Controller]
    bind[GameBoardInterface].to[model.gameBoardComponent.gameBoardBaseImpl.GameBoard]
    bind[CreatorInterface].to[model.gameBoardComponent.gameBoardBaseImpl.Creator]
    bind[RestControllerInterface].to[rest.restComponent.restBaseImpl.RestController]
    //bind[DaoInterface].to[DaoSlick]
    bind[DaoInterface].to[DaoMongoDB]
  }

}