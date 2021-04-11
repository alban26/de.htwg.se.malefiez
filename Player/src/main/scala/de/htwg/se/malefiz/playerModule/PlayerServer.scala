package de.htwg.se.malefiz.playerModule
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._

import scala.io.StdIn
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.google.inject.{Guice, Injector}

import de.htwg.se.malefiz.playerModule.controller.controllerComponent.ControllerInterface
import de.htwg.se.malefiz.playerModule.model.playerServiceComponent.{Dice, Player, PlayerService}
import spray.json._

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val playerFormat = jsonFormat2(Player)
  implicit val playerListFormat = jsonFormat4(PlayerService) // contains List[Item]
}

object PlayerServer extends JsonSupport{

  val injector: Injector = Guice.createInjector(new PlayerServerModule)
  val controller: ControllerInterface = injector.getInstance(classOf[ControllerInterface])

  def main(args: Array[String]): Unit = {



    implicit val system = ActorSystem(Behaviors.empty, "Player-Service")
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.executionContext

    val route = concat(
      (path("player") & post) {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
      },
      (path("players") & post) {
        entity(as[List[Player]]) { playerList => //

          val player_1 = playerList.toString()
          print(player_1)
          complete(s"got $player_1")
        }},
      (path("playersTurn") & get) {
         // will unmarshal JSON to Order
          val playerTurn = controller.getPlayerTurn
          complete(playerTurn)
        }
      )

    val bindingFuture = Http().newServerAt("localhost", 8080).bind(route)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}
