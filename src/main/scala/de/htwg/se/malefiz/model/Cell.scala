package de.htwg.se.malefiz.model

case class Cell(cellNumber: Int, destination: Boolean, wallPermission: Boolean, hasWall: Boolean,
                coordinates: Point) {


  val stein_or_spieler = if(cellNumber > 5) "[]" else if(hasWall==true)"[X]" else "[ ]"
  override def toString: String = stein_or_spieler

}