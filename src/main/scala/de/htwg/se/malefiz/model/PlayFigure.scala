package de.htwg.se.malefiz.model

import java.awt.Color

case class PlayFigure(playerNumber: Int, figureNumber: Int, color: Color, onField: Int) {

  override def toString:String = "figurenumber: " + this.figureNumber + " figureColor: " + this.color

}
