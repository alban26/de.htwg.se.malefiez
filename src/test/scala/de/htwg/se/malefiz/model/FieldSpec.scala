package de.htwg.se.malefiz.model

import org.scalatest.{Matchers, WordSpec}

class FieldSpec extends WordSpec with Matchers {

  "A Field" when {
    "not set to any Value" should {
      val emptyField = Field(0, 0, destination = false, wallPermission = false, Point(0,0), hasWall = false)
      "have Value 1 " in {
        emptyField.playerNumber should be(0)
      }
      "have Value 0" in {
        emptyField.playerNumber should be(0)
      }
      "have value false" in {
        emptyField.destination should be(false)
        emptyField.wallPermission should be(false)
      }
      "have value x = 0" in {
        emptyField.coordinates.x_coordinate should be(0)
      }
      "have value y = 0" in {
        emptyField.coordinates.y_coordinate should be(0)
      }
    }
    "set to a specific value" should{

    }
  }
}
