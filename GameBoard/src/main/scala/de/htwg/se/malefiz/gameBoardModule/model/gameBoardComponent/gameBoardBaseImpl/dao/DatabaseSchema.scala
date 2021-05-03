package de.htwg.se.malefiz.gameBoardModule.model.gameBoardComponent.gameBoardBaseImpl.dao

import de.htwg.se.malefiz.gameBoardModule.model.gameBoardComponent.gameBoardBaseImpl.{Cell, Player, Point}
import slick.jdbc.H2Profile.api._

trait DatabaseSchema {

  class Cells(tag: Tag) extends Table[Cell](tag, "Cells") {

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

  val cells = TableQuery[Cells]
  val players = TableQuery[Players]
  val playersTurn = TableQuery[PTurn]

  class Players(tag: Tag) extends Table[Player] (tag, "PLAYERS") {

    def playerNumber = column[Int] ("PLAYERNUMBER", O.PrimaryKey)

    def playerName = column[String]("PLAYERNAME")

    def * = (playerNumber, playerName) <> (Player.tupled, Player.unapply)
  }

  class PTurn(tag: Tag) extends Table[Player] (tag, "PLAYERSTURN") {

    def playerNumber = column[Int] ("PLAYERNUMBER", O.PrimaryKey)

    def playerName = column[String]("PLAYERNAME")

    def * = (playerNumber, playerName) <> (Player.tupled, Player.unapply)
  }

  /*
  class Statement(tag: Tag) extends Table[Statements] (tag, "Statements") {

    def statementStatus = column[String] ("STATEMENTSTATUS")

    def * = (statementStatus) <> (Statements.)
  }*/



}
