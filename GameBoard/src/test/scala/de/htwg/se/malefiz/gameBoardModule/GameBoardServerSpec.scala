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
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.{Directives, Route}
import akka.http.scaladsl.server.Directives.{complete, path, pathSingleSlash}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import de.htwg.se.malefiz.gameBoardModule.GameBoardServer.{controller, tui}
import de.htwg.se.malefiz.gameBoardModule.aview.Tui
import de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent.ControllerInterface
import de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent.controllerBaseImpl.Controller
import org.mockito.MockitoSugar
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.concurrent.{ExecutionContext, Future}

class GameBoardServerSpec extends WordSpec with Matchers with ScalaFutures with ScalatestRouteTest with MockitoSugar with GameBoardServer {

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
  lazy val routes = GameBoardServer


  val mockTui = mock[Tui]

      object TestObject extends GameBoardServer {
        val Tui = mockTui
      }


  // use the json formats to marshal and unmarshall objects in the test

  import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._


  "A Malefiz Gameboard Server" when {
    "start a new game " in {


      MockitoSugar.doNothing.when(mockTui).processInput("n start")
      //when(mockTui.processInput("n start"))
      //doNothing().when(mockTui.
      //when(mockTui.processInput("n start")).thenReturn(println("hi"))
      val request = HttpRequest(uri = "/newGame")

      request ~> Route.seal(routes) ~> check {
        status should ===(StatusCodes.Created)
      }
    }
  }


  //  val smallRoute =
  //  Directives.get {
  //    Directives.concat(
  //      pathSingleSlash {
  //        complete {
  //          "Captain on the bridge!"
  //        }
  //      },
  //      path("ping") {
  //        complete("PONG!")
  //      }
  //    )
  //  }
  //
  //  "The service" should {
  //
  //    "return a greeting for GET requests to the root path" in {
  //      // tests:
  //      Get() ~> smallRoute ~> check {
  //        responseAs[String] shouldEqual "Captain on the bridge!"
  //      }
  //    }
  //
  //  }

}
