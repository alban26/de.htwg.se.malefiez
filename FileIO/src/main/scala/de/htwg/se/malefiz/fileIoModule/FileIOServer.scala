package de.htwg.se.malefiz.fileIoModule

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import com.google.inject.{Guice, Injector}
import de.htwg.se.malefiz.fileIoModule.controller.controllerComponent.ControllerInterface
import de.htwg.se.malefiz.fileIoModule.model.fileIOComponent.fileIoJsonImpl.FileIO


object FileIOServer extends App{

  val injector: Injector = Guice.createInjector(new FileIOServerModule)
  val controller: ControllerInterface = injector.getInstance(classOf[ControllerInterface])

  implicit val system = ActorSystem(Behaviors.empty, "FileIO")
  implicit val executionContext = system.executionContext

  val route =
    concat (
      get {
        path("load") {
          controller.load()
          complete("Request succeded")
        }
      },
      post {
        path("save") {
          entity(as[String]) { game =>
            controller.save(game)
            println("GAME SAVED")
            complete("game saved")
          }
        }
      }
    )

  val bindingFuture = Http().newServerAt("localhost", 8081).bind(route)

}