package de.htwg.se.malefiz.model

case class Cell(cellNumber: Int, figure: Option[PlayFigure], destination: Boolean, wallPermission: Boolean, hasWall: Boolean,
                coordinates: Point) {

  val s = if(figure == None)"[ ]" else  "["+figure.get.toString.charAt(0)+"]"
  val stein_or_spieler = if(figure != None) s else if(hasWall==true)"[X]" else "[ ]"
  override def toString: String = stein_or_spieler

}