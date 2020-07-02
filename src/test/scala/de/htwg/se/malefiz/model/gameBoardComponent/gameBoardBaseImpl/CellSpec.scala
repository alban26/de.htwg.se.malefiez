package de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl

import org.scalatest._
import org.scalatest.matchers.should.Matchers

class CellSpec extends WordSpec with Matchers{
  "A Cell" when {
    " is new created" should {
      val cell = Cell(0, 1, 1, false, false, Point(0,0),false,false)
      "has a fieldnumber"  in {
        cell.cellNumber should be(0)
      }
      "has a playernumber if a playerfigure is standing on the cell" in {
        cell.playerNumber should be (1)
      }
      "has a playerfigure if a player is standing on the cell" in {
        cell.figureNumber should be (1)
      }
      "has a wallpermission. In this case it should be false" in {
        cell.wallPermission should be (false)
      }
      "has a wall on the cell. In this case false" in {
        cell.hasWall should be (false)
      }
    }
    " if it has a Wall, it should have a specific Tui appereance" in {
      val cellWithWall = Cell(50, 0, 0, true, true, Point(0,0), false, false)
      cellWithWall.toString should be ("[X]")
    }
    " if it has a player and Figure, it should have a specific Tui appereance" in {
      val cellPlayerAndFigure = Cell(0, 1, 2, false, false, Point(0,0), false, false)
      //cellPlayerAndFigure.toString should be ("[" + cellPlayerAndFigure.playerNumber.toString + "|" + cellPlayerAndFigure.figureNumber.toString + "]")
    }

  }

}
