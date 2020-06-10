package de.htwg.se.malefiz.model



case class Player(playerNumber: Int,playerName: String,  colour: String) {



   override def toString:String = "Playernumber: " + (playerNumber+1)
}

