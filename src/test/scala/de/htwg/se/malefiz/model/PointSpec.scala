package de.htwg.se.malefiz.model

import org.scalatest.{Matchers, WordSpec}

class PointSpec extends WordSpec with Matchers {

  "A Point " when {
    "ist not initialized with cooradinates" should {
      val point = Point(0,0)
      "have x = 0" in {
        point.getXCoordinate should be (0)
      }
      "have y = 0" in {
        point.getYCoordinate should be (0)
      }

    }
  }

}
