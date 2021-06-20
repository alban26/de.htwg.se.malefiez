package de.htwg.se.malefiz.gameBoardModule

import akka.Done
import akka.actor.ActorSystem
import org.scalatest.WordSpec
import org.scalatest.matchers.should.Matchers
import akka.actor.testkit.typed.scaladsl.ActorTestKit
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import akka.actor.testkit.typed.scaladsl.ActorTestKit
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.MediaTypes.{`application/json`, `text/plain`, `text/xml`}
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.{Directives, Route}
import akka.http.scaladsl.server.Directives.{complete, path, pathSingleSlash}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.google.inject.{Guice, Injector}
import de.htwg.se.malefiz.gameBoardModule.GameBoardServer.{controller, tui}
import de.htwg.se.malefiz.gameBoardModule.aview.Tui
import de.htwg.se.malefiz.gameBoardModule.aview.gui.SwingGui
import de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent.ControllerInterface
import de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent.controllerBaseImpl.Controller
import org.mockito.MockitoSugar
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import spray.json.DefaultJsonProtocol.{StringJsonFormat, listFormat}
import spray.json.enrichAny

import scala.concurrent.{ExecutionContext, Future}

class GameBoardServerSpec extends WordSpec with Matchers with ScalaFutures with ScalatestRouteTest {

  // the Akka HTTP route testkit does not yet support a typed actor system (https://github.com/akka/akka-http/issues/2036)
  // so we have to adapt for now
  lazy val testKit = ActorTestKit()

  implicit def typedSystem = testKit.system

  override def createActorSystem(): akka.actor.ActorSystem =
    testKit.system.classicSystem

  // Here we need to implement all the abstract members of UserRoutes.
  // We use the real UserRegistryActor to test it while we hit the Routes,
  // but we could "mock" it by implementing it in-place or by using a TestProbe
  // created with testKit.createTestProbe()
  val userRegistry = testKit.createTestProbe()
  lazy val routes = GameBoardServer.route


  //  val mockTui = mock[Tui]
  //  val mockController = mock[ControllerInterface]


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

      //MockitoSugar.doNothing.when(mockTui).processInput("n start")

      val request = HttpRequest(uri = "/newGame")

      request ~> routes ~> check {
        status should ===(StatusCodes.Created)
        contentType should ===(ContentTypes.`text/plain(UTF-8)`)
        entityAs[String] should ===("Spiel wurde erfolgreich gestartet")
      }
    }
    "a new gameboard from FileIO is loaded when " in {
      //MockitoSugar.doNothing.when(mockTui).processInput("load")

      val request = HttpRequest(uri = "/loadGame")

      request ~> routes ~> check {
        entityAs[String] should ===("Spielbrett wurde erfolgreich geoeffnet")
        contentType should ===(ContentTypes.`text/plain(UTF-8)`)
        status should ===(StatusCodes.Created)
      }
    }
    "also can load from database with " in {
      //MockitoSugar.doNothing.when(mockTui).processInput("loadFromDB")

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
      //MockitoSugar.doAnswer(true).when(mockController).evalJson(gamedata)
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
      //MockitoSugar.doAnswer(true).when(mockController).evalJson(processInput)
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
