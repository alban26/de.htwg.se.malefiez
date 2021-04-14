package de.htwg.se.malefiz.playerModule

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import com.google.inject.{Guice, Injector}
import de.htwg.se.malefiz.playerModule.controller.controllerComponent.ControllerInterface
import de.htwg.se.malefiz.playerModule.model.playerServiceComponent.{Player, PlayerService}
import spray.json._

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val playerFormat: RootJsonFormat[Player] = jsonFormat2(Player)
  implicit val playerListFormat: RootJsonFormat[PlayerService] = jsonFormat4(PlayerService)
}

object PlayerServer extends JsonSupport{

  val injector: Injector = Guice.createInjector(new PlayerServerModule)
  val controller: ControllerInterface = injector.getInstance(classOf[ControllerInterface])

  def main(args: Array[String]): Unit = {

    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "Player-Service")
    implicit val executionContext: ExecutionContextExecutor = system.executionContext

    val route = concat(
      (path("players") & post) {
        entity(as[List[String]]) { playerList => //
          println("LOGGER: ", playerList.toString())
          controller.updatePlayerList(playerList)
          complete(playerList)
        }},
      (path("rollDice") & get) {
        extractRequest { request =>
          println("LOGGER: Dice rolled")
          complete(controller.rollDice.get.toString)
        }},
      (path("playersTurn") & get) {
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
