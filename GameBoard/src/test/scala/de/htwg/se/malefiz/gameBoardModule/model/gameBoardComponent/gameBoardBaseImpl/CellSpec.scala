package de.htwg.se.malefiz.gameBoardModule.model.gameBoardComponent.gameBoardBaseImpl

import org.scalatest._
import org.scalatest.matchers.should.Matchers

class CellSpec extends WordSpec with Matchers{

  "A Cell" when {
    " is new created" should {
      val cell = Cell(0, 1, 1, wallPermission = false, hasWall = false, Point(0,0),possibleFigures = false,possibleCells = false)
      "has a fieldNumber"  in {
        cell.cellNumber should be(0)
      }
      "has a playerNumber if a playerFigure is standing on the cell" in {
        cell.playerNumber should be (1)
      }
      "has a playerFigure if a player is standing on the cell" in {
        cell.figureNumber should be (1)
      }
      "has a wallPermission. In this case it should be false" in {
        cell.wallPermission should be (false)
      }
      "has a wall on the cell. In this case false" in {
        cell.hasWall should be (false)
      }
    }
    " if it has a Wall, it should have a specific Tui appereance" in {
      val cellWithWall = Cell(50, 0, 0, wallPermission = true, hasWall = true, Point(0,0), possibleFigures = false, possibleCells = false)
      cellWithWall.toString should be ("X")
    }
    " if it has a player and Figure, it should have a specific Tui appereance" in {
      val cellPlayerAndFigure = Cell(0, 1, 2, wallPermission = false, hasWall = false, Point(0,0), possibleFigures = false, possibleCells = false)
      cellPlayerAndFigure.toString should be ("1")
    }
    "" in {
      val cellPlayerAndFigure = Cell(0, 1, 2, wallPermission = false, hasWall = false, Point(0,0), possibleFigures = true, possibleCells = false)
      cellPlayerAndFigure.toString should be ("1|2 ")
    }
    "if cell has a wall and possibleCell is true" in {
      val cellPlayerAndFigure = Cell(40, 1, 2, wallPermission = true, hasWall = true, Point(0,0), possibleFigures = true, possibleCells = true)
      cellPlayerAndFigure.toString should be ("X|40")
    }
    "if cell.possibleCell is true" in {
      val cellPlayerAndFigure = Cell(35, 0, 0, wallPermission = false, hasWall = false, Point(0,0), possibleFigures = true, possibleCells = true)
      cellPlayerAndFigure.toString should be ("35")
    }
    "if cell.possibleCell is true and a player is an the cell" in {
      val cellPlayerAndFigure = Cell(35, 3, 0, wallPermission = false, hasWall = false, Point(0,0), possibleFigures = true, possibleCells = true)
      cellPlayerAndFigure.toString should be ("3|35")
    }
  }
}
