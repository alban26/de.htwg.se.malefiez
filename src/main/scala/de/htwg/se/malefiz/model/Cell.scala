package de.htwg.se.malefiz.model

case class Cell(cellNumber: Int, playerNumber: Int, destination: Boolean, wallPermission: Boolean, hasWall: Boolean,
                coordinates: Point) {

  override def toString:String = "[ ]"

}