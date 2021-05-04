package de.htwg.se.malefiz.gameBoardModule.model.dbComponent

import de.htwg.se.malefiz.gameBoardModule.model.gameBoardComponent.GameBoardInterface

import scala.concurrent.Future

trait DaoInterface {

  def load(): GameBoardInterface

  def save(gameBoardInterface: GameBoardInterface): Future[Unit]

}
