package de.htwg.se.malefiz.playerModule.model

import de.htwg.se.malefiz.playerModule.model.playerServiceComponent.{Player, PlayerService}

trait PlayerServiceInterface {

  def playerList: List[Option[Player]]
  def playersTurn: Option[Player]
  def dicedNumber: Option[Int]
  def selectedFigure: Option[(Int, Int)]
  def rollDice: PlayerService

  def updatePlayerList(players: List[String]): PlayerService

  def updatePlayersTurn(player: Option[Player]): PlayerService

}
