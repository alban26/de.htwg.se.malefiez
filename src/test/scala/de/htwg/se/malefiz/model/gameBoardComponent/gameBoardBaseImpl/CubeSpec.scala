package de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl

import org.scalatest._
import org.scalatest.matchers.should.Matchers

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
