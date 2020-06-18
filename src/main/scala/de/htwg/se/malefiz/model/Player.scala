package de.htwg.se.malefiz.model

case class Player(playerNumber: Int, name: String) {



   override def toString:String = playerNumber+ " -->  " + name
}

