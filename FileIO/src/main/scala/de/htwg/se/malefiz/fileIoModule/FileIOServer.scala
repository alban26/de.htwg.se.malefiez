package de.htwg.se.malefiz.fileIoModule

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import de.htwg.se.stratego.model.fileIoComponent.fileIoJsonImpl.FileIO

object FileIOServer extends App{

  val fileIO = new FileIO
  implicit val system = ActorSystem(Behaviors.empty, "FileIO")
  implicit val executionContext = system.executionContext

  val route =
    concat (
      get {
        path("json") {
          complete(HttpEntity(ContentTypes.`application/json`, fileIO.load))
        }
      },
      post {
        path("json") {
          entity(as [String]) { game =>
            fileIO.save(game)
            println("GAME SAVED")
            complete("game saved")
          }
        }
      }
    )

  val bindingFuture = Http().newServerAt("localhost", 8081).bind(route)

}