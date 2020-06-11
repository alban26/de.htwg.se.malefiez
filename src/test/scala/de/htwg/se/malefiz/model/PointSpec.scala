package de.htwg.se.malefiz.model

import org.scalatest.{Matchers, WordSpec}

class PointSpec extends WordSpec with Matchers {

  "A Point " when {
    "ist not initialized with cooradinates" should {
      val point = Point(0,0)
      val x = point.x_coordinate
      val y = point.y_coordinate
      val string = point.toString
      "have x = 0" in {
        x should be (0)
      }
      "have y = 0" in {
        y should be (0)
      }
      "have a string representation" in {
        string should be ("x: 0 y: 0")
      }


    }
  }

}
