package de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl

import org.scalatest._
import org.scalatest.matchers.should.Matchers

class CellSpec extends WordSpec with Matchers{
  "A Cell" when {
    "new" should {
      val cell = Cell(0, 1, 1, false, false, false, Point(0,0),false,false)
      "have a fieldnumber"  in {
        cell.cellNumber should be(0)
      }
      "have a playernumber if a playerfigure is standing on the cell" in {
        cell.playerNumber should be (1)
      }
      "have a playerfigure if a player is standing on the cell" in {
        cell.figureNumber should be (1)
      }
      "have Destination = false if its not the destination Cell" in {
        cell.destination should be (false)
      }
      "have a wallpermission. In this case it should be false" in {
        cell.wallPermission should be (false)
      }
      "have a wall on the cell. In this case false" in {
        cell.hasWall should be (false)
      }
    }
  }

}
