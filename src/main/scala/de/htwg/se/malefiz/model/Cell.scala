package de.htwg.se.malefiz.model

import java.awt.Color

case class Cell(cellNumber: Int, playerNumber: Int, figureNumber: Int, destination: Boolean, wallPermission: Boolean, hasWall: Boolean,
                coordinates: Point) {

 // val s = if(playFigure.numberOfPlayer == 0)"[ ]" else  "["+playFigure.numberOfPlayer+"]"


  var col = Color.GRAY

  val colours : List[Color] = List(Color.blue, Color.red, Color.green, Color.yellow)

  val stein_or_spieler =
    if(playerNumber != 0 && !hasWall) {
       "("  + playerNumber + ")"
    } else if(playerNumber == 2 && !hasWall) {
      "("  + playerNumber + ")"
    } else if(playerNumber == 3 && !hasWall) {
       "(" + playerNumber + ")"
    } else if(playerNumber == 4 && !hasWall) {
       "(" + playerNumber + ")"
    } else if (hasWall) {
      "[X]"
    } else {
      "[ ]"
    }



  override def toString: String = stein_or_spieler

}