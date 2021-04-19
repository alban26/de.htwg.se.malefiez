package de.htwg.se.malefiz.gameBoardModule.model.gameBoardComponent.gameBoardBaseImpl

import org.scalatest._
import org.scalatest.matchers.should.Matchers

class DiceSpec extends WordSpec with Matchers {
  "A Cube " when {
    "is thrown " should {
      val randomNumber = Dice().rollDice
      "shows a number between 1 and 6" in {
        randomNumber.get should (be >= 1 and be < 7)
      }
    }
  }
}
