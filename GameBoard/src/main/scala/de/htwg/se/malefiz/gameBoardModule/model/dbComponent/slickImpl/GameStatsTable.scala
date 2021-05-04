package de.htwg.se.malefiz.gameBoardModule.model.dbComponent.slickImpl

import slick.jdbc.MySQLProfile.api._

case class GameStats(playerNumber: Int, figureNumber: Int, dicedNumber: Int, stateNumber: Int)

class GameStatsTable(tag: Tag) extends Table[GameStats](tag, "GAMESTATS") {

  def playerNumber = column[Int]("PLAYERNUMBER", O.PrimaryKey)

  def figureNumber = column[Int]("FigureNumber")

  def dicedNumber = column[Int]("DicedNumber")

  def stateNumber = column[Int]("StateNumber")

  def * = (playerNumber, figureNumber, dicedNumber, stateNumber) <> (GameStats.tupled, GameStats.unapply)
}
