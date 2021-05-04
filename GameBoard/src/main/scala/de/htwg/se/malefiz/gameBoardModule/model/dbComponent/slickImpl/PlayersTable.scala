package de.htwg.se.malefiz.gameBoardModule.model.dbComponent.slickImpl

import de.htwg.se.malefiz.gameBoardModule.model.gameBoardComponent.gameBoardBaseImpl.Player
import slick.jdbc.MySQLProfile.api._

class PlayersTable(tag: Tag) extends Table[Player](tag, "PLAYERS") {

  def playerNumber = column[Int]("PLAYERNUMBER", O.PrimaryKey)

  def playerName = column[String]("PLAYERNAME")

  def * = (playerNumber, playerName) <> (Player.tupled, Player.unapply)
}
