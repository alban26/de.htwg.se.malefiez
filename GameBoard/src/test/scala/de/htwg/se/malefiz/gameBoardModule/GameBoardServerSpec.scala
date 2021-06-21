package de.htwg.se.malefiz.gameBoardModule

import akka.actor.testkit.typed.scaladsl.ActorTestKit
import akka.http.scaladsl.model.MediaTypes.`application/json`
import akka.http.scaladsl.model._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import de.htwg.se.malefiz.gameBoardModule.GameBoardServer.controller
import org.scalatest.WordSpec
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers
import spray.json.DefaultJsonProtocol.{StringJsonFormat, listFormat}
import spray.json.enrichAny

class GameBoardServerSpec extends WordSpec with Matchers with ScalaFutures with ScalatestRouteTest {

  lazy val testKit = ActorTestKit()

  implicit def typedSystem = testKit.system

  override def createActorSystem(): akka.actor.ActorSystem =
    testKit.system.classicSystem

  lazy val routes = GameBoardServer.route


  "When a Malefiz Gameboard Server is running, " when {
    "the server is able to receive a list of players" in {

      val players = List("Robert", "Alban")
      val postRequest = HttpRequest(
        HttpMethods.POST,
        uri = "/players",
        entity = HttpEntity(`application/json`, players.toJson.toString)
      )

      postRequest ~> routes ~> check {
        status should ===(StatusCodes.Created)
      }
    }
    "then start a new game " in {

      val request = HttpRequest(uri = "/newGame")

      request ~> routes ~> check {
        status should ===(StatusCodes.Created)
        contentType should ===(ContentTypes.`text/plain(UTF-8)`)
        entityAs[String] should ===("Spiel wurde erfolgreich gestartet")
      }
    }
    "a new gameboard from FileIO is loaded when " in {

      val request = HttpRequest(uri = "/loadGame")

      request ~> routes ~> check {
        entityAs[String] should ===("Spielbrett wurde erfolgreich geoeffnet")
        contentType should ===(ContentTypes.`text/plain(UTF-8)`)
        status should ===(StatusCodes.Created)
      }
    }
    "also can load from database with " in {

      val request = HttpRequest(uri = "/loadGameFromDB")

      request ~> routes ~> check {
        entityAs[String] should ===("Spiel wurde erfolgreich geladen")
        contentType should ===(ContentTypes.`text/plain(UTF-8)`)
        status should ===(StatusCodes.Created)
      }
    }
    "open the gameboard " in {

      val request = HttpRequest(uri = "/gameBoard")

      request ~> routes ~> check {
        entityAs[String] should ===("Spiel wurde geöffnet")
        contentType should ===(ContentTypes.`text/plain(UTF-8)`)
        status should ===(StatusCodes.Accepted)
      }
    }
    "Server loads the game which is received from FileIO " in {
      val gamedata = "gamedata..."
      val postRequest = HttpRequest(
        HttpMethods.POST,
        uri = "/gameBoardJson",
        entity = HttpEntity(`application/json`, gamedata.toJson.toString)
      )

      postRequest ~> routes ~> check {
        entityAs[String] should ===("Laden von Json war erfolgreich!")
        contentType should ===(ContentTypes.`text/plain(UTF-8)`)
        status should ===(StatusCodes.Created)
      }
    }
    "Tui also executes input by process" in {
      val processInput = "input..."
      val postRequest = HttpRequest(
        HttpMethods.POST,
        uri = "/process",
        entity = HttpEntity(`application/json`, processInput.toJson.toString)
      )

      postRequest ~> routes ~> check {
        entityAs[String] should ===(controller.gameBoard.toString)
        contentType should ===(ContentTypes.`text/plain(UTF-8)`)
        status should ===(StatusCodes.Accepted)
      }
    }
  }

}
