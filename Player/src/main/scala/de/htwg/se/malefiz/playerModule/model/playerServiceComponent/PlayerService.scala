package de.htwg.se.malefiz.playerModule.model.playerServiceComponent

import de.htwg.se.malefiz.playerModule.model.PlayerServiceInterface


case class PlayerService(playerList: List[Option[Player]],
                         playersTurn: Option[Player],
                         selectedFigure: Option[(Int, Int)],
                         dicedNumber: Option[Int])
  extends PlayerServiceInterface {

  def this() = this(
    List.empty,
    Option.empty,
    Option.empty,
    Option.empty,
  )

  override def updatePlayerList(players: List[Option[Player]]): PlayerService = copy(playerList = players)

  override def updatePlayersTurn(player: Option[Player]): PlayerService = copy(playersTurn = player)

  override def rollDice: PlayerService = copy(dicedNumber = Dice().rollDice)


}