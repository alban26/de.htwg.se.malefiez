package de.htwg.se.malefiz.model

import java.awt.Color
import org.scalatest.matchers.should.Matchers
import org.scalatest._

class PlayFigureSpec extends WordSpec with Matchers{
  "A PlayerFigure" when {
    "new" should {
      val playerFigure =  PlayFigure(1, 1, Color.red, 5)
      "have a PlayerNumber"  in {
       playerFigure.playerNumber should be (1)
      }
      "have a color respresentation" in {
        playerFigure.color should be (Color.red)
      }
      "have a Player number" in {
        playerFigure.onField should be (5)
      }
    }
  }
}
