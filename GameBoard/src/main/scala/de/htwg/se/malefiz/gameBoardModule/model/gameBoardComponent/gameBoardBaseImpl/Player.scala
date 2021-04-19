package de.htwg.se.malefiz.gameBoardModule.model.gameBoardComponent.gameBoardBaseImpl

case class Player(playerNumber: Int, name: String) {

  override def toString: String = name

}
