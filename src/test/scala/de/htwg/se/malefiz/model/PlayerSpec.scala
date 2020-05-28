package de.htwg.se.malefiz.model

import java.awt.Color

import org.scalatest._

class PlayerSpec extends WordSpec with Matchers {
  "A Player" when {
    "new" should {
    val player = new Player("Your Name", "Rot", 1)
      "have a name"  in {
        player.name should be("Your Name")
      }
      "have a color respresentation" in {
      player.color should be(Color.red)
      }
      "have a Player number" in {
        player.numberOfPlayer should be (1)
      }
    }
  }
}

