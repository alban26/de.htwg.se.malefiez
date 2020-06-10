package de.htwg.se.malefiz.model

import org.scalatest.matchers.should.Matchers
import org.scalatest._

class CubeSpec extends WordSpec with Matchers {

  "A Cube " when {
    "is thrown " should {
      val randomNumber = Cube().getRandomNumber
      "be between 1 and 6" in {
        randomNumber should (be >= 1 and be < 7)
      }
    }
  }
}
