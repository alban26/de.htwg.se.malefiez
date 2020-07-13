package de.htwg.se.malefiz.aview.gui

import java.awt.{Color, Point}

import de.htwg.se.malefiz.Malefiz
import org.scalatest.matchers.should.Matchers
import org.scalatest._

import scala.swing.event.MouseClicked

class SwingGuiSpec extends WordSpec with Matchers {

  "The Swing Gui" when {
    "the Game is started" should {

      val newGame = Malefiz

      newGame.tui.processInput("n Robert")
      newGame.tui.processInput("n Alban")
      newGame.tui.processInput("n Denis")
      newGame.tui.processInput("n Dima")

      newGame.tui.processInput("start")
      newGame.tui.processInput("r")
      newGame.tui.processInput("1 1")
      "update playerArea" in {
        newGame.swingGui.updatePlayerArea() should be (true)
      }
      "update informationArea" in {
        newGame.swingGui.updateInformationArea() should be (true)
      }
      "update randomNumberArea" in {
        newGame.swingGui.updateRandomNumberArea() should be (true)
      }
      "uodate playerTurnArea" in {
        newGame.swingGui.updatePlayerTurn() should be (true)
      }
      "draw Circles" in {
        newGame.swingGui.drawCircle(50,50, Color.CYAN) should be (true)
      }
      "calculate x and y coordinates" in {
        val x = newGame.swingGui.getRange(50)
        x.contains(30) should be (true)
        x.contains(70) should be (true)
        val y = newGame.swingGui.getRange(80)
        y.contains(60) should be (true)
        y.contains(100) should be (true)
      }
    }
  }

}
