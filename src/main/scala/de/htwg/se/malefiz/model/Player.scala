package de.htwg.se.malefiz.model

import java.awt.Color

case class Player(name: String, figure: PlayFigure) {


   /* color match {
      case "Red" =>  playerColor = Color.red
      case "Green" => playerColor = Color.green
      case "Yellow" => playerColor = Color.yellow
      case "Blue" => playerColor = Color.blue
   }
*/
   override def toString: String = name
}

