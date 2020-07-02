package de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl

import de.htwg.se.malefiz.model.playerComponent.Player
import org.scalatest._
import org.scalatest.matchers.should.Matchers

class PlayerSpec extends WordSpec with Matchers {
  "A Player" when {
    "new" should {
    val player = Player(1, "Robert")
      "have a name"  in {
        player.name should be("Robert")
      }
      "have a Player number" in {
        player.playerNumber should be (1)
      }
    }
  }
}
