package de.htwg.se.malefiz.model

import java.awt.Color

case class Cell(cellNumber: Int, playerNumber: Int, figureNumber: Int, destination: Boolean, wallPermission: Boolean, hasWall: Boolean,
                coordinates: Point) {


  val stein_or_spieler =
    if(cellNumber < 20) {
      if(playerNumber != 0 && !hasWall) {
        "("  + playerNumber + ")"
      }  else {
        "( )"
      }
    } else {
    if(playerNumber != 0 && !hasWall) {
       "["  + playerNumber + "]"
    }  else if (hasWall) {
      "[X]"
    } else {
      "[ ]"
    }
    }


  override def toString: String = stein_or_spieler//"["+cellNumber+"]"

}