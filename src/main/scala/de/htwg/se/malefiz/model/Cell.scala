package de.htwg.se.malefiz.model

case class Cell(cellNumber: Int, player: Player, destination: Boolean, wallPermission: Boolean, hasWall: Boolean,
                coordinates: Point) {

  //val s = if(playFigure.numberOfPlayer == 0)"[ ]" else  "["+playFigure.numberOfPlayer+"]"
  val stein_or_spieler = if(hasWall==true)"[X]" else "[ ]"
  override def toString: String = stein_or_spieler + player

}