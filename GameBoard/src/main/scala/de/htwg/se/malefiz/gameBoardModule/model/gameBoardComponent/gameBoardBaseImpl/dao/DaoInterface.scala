package de.htwg.se.malefiz.gameBoardModule.model.gameBoardComponent.gameBoardBaseImpl.dao

import com.google.inject.Inject
import de.htwg.se.malefiz.gameBoardModule.model.gameBoardComponent.GameBoardInterface
import slick.jdbc.H2Profile.api._

import scala.concurrent.Future

trait DaoInterface  {

  def load() : GameBoardInterface

  def save(gameBoardInterface: GameBoardInterface) : Future[Unit]


}
