import akka.actor.testkit.typed.scaladsl.ActorTestKit
import akka.http.scaladsl.model.MediaTypes.{`application/json`, `text/xml`}
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpMethods, HttpRequest, StatusCodes}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import de.htwg.se.malefiz.fileIoModule.FileIOServer
import de.htwg.se.malefiz.gameBoardModule.GameBoardServer.controller
import org.scalatest.WordSpec
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers
import spray.json.DefaultJsonProtocol.{StringJsonFormat, listFormat}
import spray.json.enrichAny

class FileIOServerSpec extends WordSpec with Matchers with ScalaFutures with ScalatestRouteTest {

  lazy val testKit = ActorTestKit()

  implicit def typedSystem = testKit.system

  override def createActorSystem(): akka.actor.ActorSystem =
    testKit.system.classicSystem

  lazy val routes = FileIOServer.route


  "When FileIO Server starts, " when {
    "Saving something as json " in {

      val gameboard = "gameboard..."
      val postRequest = HttpRequest(
        HttpMethods.POST,
        uri = "/save",
        entity = HttpEntity(`application/json`, gameboard.toJson.toString)
      )


      postRequest ~> routes ~> check {
        status should ===(StatusCodes.OK)
        contentType should ===(ContentTypes.`text/plain(UTF-8)`)
        entityAs[String] should ===("Erfolgreich gespeichert!")
      }
    }
    "Also loading is possible by" in {
      val request = HttpRequest(uri = "/load")

      request ~> routes ~> check {
        entityAs[String] should ===("Erfolgreich geladen!")
        contentType should ===(ContentTypes.`text/plain(UTF-8)`)
        status should ===(StatusCodes.OK)
      }
    }
  }

}
