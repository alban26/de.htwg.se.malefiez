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



  override def updatePlayersTurn(player: Option[Player]): PlayerService = copy(playersTurn = player)

  override def rollDice: PlayerService = copy(dicedNumber = Dice().rollDice)

  override def updatePlayerList(players: List[String]): PlayerService = copy(playerList = createPlayerList(players, 0))

  def createPlayerList(pList: List[String], n: Int): List[Option[Player]] = {
    if (n > pList.length - 1)
      Nil
    else if (pList(n) == "")
      createPlayerList(pList, n + 1)
    else
      Option(new Player(n, pList(n))) +: createPlayerList(pList, n + 1)
  }


}