package de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl

case class Point (x_coordinate: Int, y_coordinate: Int) {

  override def toString:String = "x: " + this.x_coordinate + " y: " + this.y_coordinate

}
