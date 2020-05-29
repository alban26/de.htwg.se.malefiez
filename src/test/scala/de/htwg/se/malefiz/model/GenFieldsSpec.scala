package de.htwg.se.malefiz.model

import de.htwg.se.malefiz.model
import org.scalatest.{Matchers, WordSpec}

class GenFieldsSpec extends WordSpec with Matchers {

  "The Gameborad ist respresented by a list of Field-Objekts. The Gameboard" when {
    "is generated" should {
      val originalGameboard = new GenFields
      val gameboard = originalGameboard.genCells(4)
      "should be an Instance of List of Fields" in {
        gameboard.isInstanceOf[List[Field]] should be(true)
      }
      "contain 112 Elements in the List " in {
        gameboard.length should be(113)
      }
      "determine the Number of generated Nodes" in {
        originalGameboard.numberOfNodes(4) should be (113)
      }
      "the field is divided in rows - a part of them are the singly ones" in {
        originalGameboard.singleNodesBetween(4,3) should be(18)
      }
      "the other Nodes in between" in {
        originalGameboard.nodesBetween(4) should be(44)
      }
      "the first(bottom) and the last 2 rows are generated" in {
        originalGameboard.bottomTopNodes(4) should be (51)
      }
      "the line in the Bottom" in {
        originalGameboard.s(4) should be(17)
      }
      "the first row of walls is represented by a List " in {
        originalGameboard.steineUnten(4)(0) should be (22)
        originalGameboard.steineUnten(4)(1) should be (26)
        originalGameboard.steineUnten(4)(2) should be (30)
        originalGameboard.steineUnten(4)(3) should be (34)
        originalGameboard.steineUnten(4)(4) should be (38)
      }
      "steineUnten also has a helper Method " in {
        originalGameboard.steineUntenHilf(7 + (3*5),4).length should be(5)
        originalGameboard.steineUntenHilf(7 + (3*5),4)(0) should be(22)
        originalGameboard.steineUntenHilf(7 + (3*5),4)(1) should be(26)
        originalGameboard.steineUntenHilf(7 + (3*5),4)(2) should be(30)
        originalGameboard.steineUntenHilf(7 + (3*5),4)(3) should be(34)
        originalGameboard.steineUntenHilf(7 + (3*5),4)(4) should be(38)
      }
      "every gameboard has its typical 3 Walls at the top" in {
        originalGameboard.dreiSteine(4).length should be(3)
        originalGameboard.dreiSteine(4)(0) should be (72)
        originalGameboard.dreiSteine(4)(1) should be (75)
        originalGameboard.dreiSteine(4)(2) should be (84)
      }
      "there are also two walls in the middle of the field" in {
        val s = originalGameboard.dreiSteine(4)
        originalGameboard.twoWalls(s).length should be(2)
        originalGameboard.twoWalls(s)(0) should be(61)
        originalGameboard.twoWalls(s)(1) should be(65)
      }
      "the last but one wall" in {
        originalGameboard.wallBeforeAim(4)(0) should be(103)
      }
      "all walls together" in {
        originalGameboard.buildWalls(4).length should be(11)
      }
      "the first 17 Elements shouldn't have any permission to set a Wall" in {
        for (i <- 0 to 16)
          gameboard(i).wallPermission should be(false)
      }


      }

    }



}
