package de.htwg.se.malefiz.gameBoardModule.model.dbComponent.slickImpl

import de.htwg.se.malefiz.gameBoardModule.model.gameBoardComponent.gameBoardBaseImpl.{Cell, Point}
import slick.jdbc.MySQLProfile.api._

class CellsTable (tag: Tag) extends Table[Cell](tag, "Cells") {

  def cellNumber = column[Int]("ID", O.PrimaryKey)

  def playerNumber = column[Int]("PLAYERNUMBER")

  def figureNumber = column[Int]("FIGURENUMBER")

  def wallPermission = column[Boolean]("WALLPERMISSION")

  def hasWall = column[Boolean]("HASWALL")

  def xCoordinate = column[Int]("X-COORD")

  def yCoordinate = column[Int]("Y-COORD")

  def possibleFigures = column[Boolean]("POSSIBLEFIGURES")

  def possibleCells = column[Boolean]("POSSIBLECELLS")

  def * = (cellNumber,
    playerNumber,
    figureNumber,
    wallPermission,
    hasWall,
    xCoordinate,
    yCoordinate,
    possibleFigures,
    possibleCells
  ) <> (create, extract)

  def create(t: (Int, Int, Int, Boolean, Boolean, Int, Int, Boolean, Boolean)): Cell = {
    Cell(t._1, t._2, t._3, t._4, t._5, Point(t._6, t._7), t._8, t._9)
  }

  def extract(c: Cell): Option[(Int, Int, Int, Boolean, Boolean, Int, Int, Boolean, Boolean)] = {
    Some((c.cellNumber, c.playerNumber, c.figureNumber, c.wallPermission, c.hasWall, c.coordinates.x_coordinate, c
      .coordinates.y_coordinate, c.possibleFigures, c.possibleCells))
  }
}