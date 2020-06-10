package de.htwg.se.malefiz.model

case class Cell(cellNumber: Int, figure: PlayFigure, destination: Boolean, wallPermission: Boolean, hasWall: Boolean,
                coordinates: Point) {

  val s = if(figure == null)"[ ]" else  "["+figure.toString.charAt(0)+"]"
  val stein_or_spieler = if(figure != null) s else if(hasWall==true)"[X]" else "[ ]"
  override def toString: String = stein_or_spieler

}