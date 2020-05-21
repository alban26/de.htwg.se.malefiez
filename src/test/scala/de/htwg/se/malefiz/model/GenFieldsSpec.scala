package de.htwg.se.malefiz.model

import de.htwg.se.malefiz.model
import org.scalatest.{Matchers, WordSpec}

class GenFieldsSpec extends WordSpec with Matchers {

  "The Gameborad ist respresented by a list of Field-Objekts. The Gameboard" when {
    "is generated" should {
      val originalGameboard = new GenFields
      val gameboard = originalGameboard.genCells(112)
      "should be an Instance of List of Fields" in {
        gameboard.isInstanceOf[List[Field]] should be(true)
      }
      "contain 112 Elements in the List " in {
        gameboard.length should be(112)
      }
      "the first 17 Elements shouldnt have any permission to set a Wall" in {
        for (i <- 0 to 16)
          gameboard(i).wallPermission should be(false)
      }
      "the fields from 17 to 110 should have permission to set a Wall " in {
        for (i <- 17 to 110)
          gameboard(i).wallPermission should be(true)
      }
      "has set Walls on specific field numbers," in {
        gameboard(22).hasWall should be(true)
        gameboard(26).hasWall should be(true)
        gameboard(30).hasWall should be(true)
        gameboard(34).hasWall should be(true)
        gameboard(38).hasWall should be(true)
        gameboard(60).hasWall should be(true)
        gameboard(64).hasWall should be(true)
        gameboard(71).hasWall should be(true)
        gameboard(74).hasWall should be(true)
        gameboard(83).hasWall should be(true)
        gameboard(102).hasWall should be(true)
      }
      "the destination of the game should be 111" in {
        gameboard(111).destination should be(true)
      }
      "is generated for test purposed with only 10 Field" in {
        val testGameboeard = new GenFields
        val testBoard = testGameboeard.genCells(10)
        testBoard.length should be (10)
      }

    }
  }
}
