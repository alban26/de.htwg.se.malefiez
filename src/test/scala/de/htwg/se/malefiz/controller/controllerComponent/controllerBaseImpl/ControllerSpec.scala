package de.htwg.se.malefiz.controller.controllerComponent.controllerBaseImpl

import org.scalatest._
import org.scalatest.matchers.should.Matchers

import scala.util.Left

class ControllerSpec extends WordSpec with Matchers {
  "A Controller" when {

    "observed by an Observer" should {

      val controller = new Controller()

      "should check the input from tui" in {
        val wrongInput = "A B C D E"
        controller.checkInput(wrongInput) should be(Left("Bitte maximal 4 Spieler eintippen (leerzeichen-getrennt)!"))
      }
      "when entry some input " in {
        controller.execute("someInput") should be (true)
      }
      "load from File" in {
        controller.loadFromDB() should be (true)
      }
      "starting the game" in {
        controller.startGame() should be (true)
      }
      "When sending players to game service" in {
        val playerList: List[String] = List("alban", "robert")
        controller.sendPlayersToGameService(playerList)
      }
      "when loading from database" in {
        controller.loadFromDB() should be (true)
      }
    }
  }
}
