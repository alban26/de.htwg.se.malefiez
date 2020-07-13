package de.htwg.se.malefiz.aview.gui

import de.htwg.se.malefiz.Malefiz
import org.scalatest.matchers.should.Matchers
import org.scalatest._

class SwingGuiSpec extends WordSpec with Matchers {

  "The Swing Gui" when {
    "the Game is started" should {

      val newGame = Malefiz

      newGame.tui.processInput("n Robert")
      newGame.tui.processInput("n Alban")
      newGame.tui.processInput("start")
      newGame.tui.processInput("r")
      "update player era" in {
        newGame.swingGui.updatePlayerArea() should be (true)
      }
      "update informationArea" in {
        newGame.swingGui.updateInformationArea() should be (true)
      }
      "update randomNumberArea" in {
        newGame.swingGui.updateRandomNumberArea() should be (true)
      }
      "uodate playerTrunArea" in {
        newGame.swingGui.updatePlayerTurn() should be (true)
      }
    }
  }

}
