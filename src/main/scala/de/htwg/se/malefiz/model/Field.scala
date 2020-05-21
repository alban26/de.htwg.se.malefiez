package de.htwg.se.malefiz.model

case class Field(fieldNumber: Int, playerNumber: Int, destination: Boolean,
                 wallPermission: Boolean, coordinates: Point, hasWall: Boolean) {

  def getFieldNumber : Int = this.fieldNumber;
  def getPlayerNumber : Int = this.playerNumber;
  def isDestination: Boolean = this.destination;
  def getWallPermission : Boolean = this.wallPermission
  def getXCoordinate: Int = this.coordinates.x_coordinate
  def getYCoordinate: Int = this.coordinates.y_coordinate

  override def toString: String = {
    "[ ]" + fieldNumber
  }


}

