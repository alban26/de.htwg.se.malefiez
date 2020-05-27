package de.htwg.se.malefiz.model

case class Point(x_coordinate: Int, y_coordinate: Int) {

  def getXCoordinate : Int = this.x_coordinate
  def getYCoordinate : Int = this.y_coordinate

  override def toString:String = "x: " + this.x_coordinate + " y: " + this.y_coordinate

}

