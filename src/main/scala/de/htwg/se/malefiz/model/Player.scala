package de.htwg.se.malefiz.model

case class Player(name: String) {
   val colours = Array("Rot", "Grün", "Gelb", "Blau")
   val playerColor = ""

   def setPlayerColour(color: String) = {



   }

   override def toString:String = name


}

