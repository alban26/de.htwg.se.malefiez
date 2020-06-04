package de.htwg.se.malefiz.model

import java.awt.Color

case class Player(playerNumber: Int, colour: String, cellList: List[Cell]) {
  val numberOfPlayer: Int = playerNumber
  var playerColour: Color = Color.black
  val playerFigures = new Array[PlayFigure](5)

  for (i <- playerFigures.indices) {
    numberOfPlayer match {
      case 0 => playerFigures(i) = PlayFigure(i, cellList.head)
      case 1 => playerFigures(i) = PlayFigure(i, cellList(1))
      case 2 => playerFigures(i) = PlayFigure(i, cellList(2))
      case 3 => playerFigures(i) = PlayFigure(i, cellList(3))
    }
  }

  colour match {
      case "Red" =>  playerColour = Color.red
      case "Green" => playerColour = Color.green
      case "Yellow" => playerColour = Color.yellow
      case "Blue" => playerColour = Color.blue
   }


   override def toString:String = "Playernumber: " + playerNumber// + " Player: " + name + " Color: " + playerColor + figuresOfPlayer
}

