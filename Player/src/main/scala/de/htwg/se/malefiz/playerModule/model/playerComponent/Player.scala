package de.htwg.se.malefiz.playerModule.model.playerComponent

case class Player(playerNumber: Int, name: String) {

  override def toString: String = name

}
