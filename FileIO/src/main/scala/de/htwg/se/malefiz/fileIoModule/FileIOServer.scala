package de.htwg.se.malefiz.fileIoModule

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentType, ContentTypes, HttpEntity, MediaRange, MediaTypes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{MediaTypeNegotiator, UnsupportedRequestContentTypeRejection}
import com.google.inject.{Guice, Injector}
import de.htwg.se.malefiz.fileIoModule.FileIOServer.controller
import de.htwg.se.malefiz.fileIoModule.controller.controllerComponent.ControllerInterface

import scala.concurrent.Future
import scala.concurrent.duration.{DurationInt, SECONDS}
import scala.util.{Failure, Success}


object FileIOServer extends App {

  val injector: Injector = Guice.createInjector(new FileIOServerModule)
  val controller: ControllerInterface = injector.getInstance(classOf[ControllerInterface])

  implicit val system = ActorSystem(Behaviors.empty, "FileIO")
  implicit val executionContext = system.executionContext

  //val myEncodings = Seq(MediaRange(MediaTypes.`application/json`), MediaRange(MediaTypes.`application/xml`))

  val route =
    concat(
      path("save") {
        (post & extract(_.request.entity)) { body =>
          complete {
            if (body.getContentType() == ContentTypes.`text/xml(UTF-8)`) {
              val entityFuture: Future[String] = body.toStrict(1.seconds).map(_.data.decodeString("UTF-8"))
              entityFuture.onComplete {
                case Success(value)  =>
                  println("Speichere als XML")
                  controller.save(value, "xml")
                  HttpEntity("Speichern als XML erfolgreich")
                case Failure(exception) => HttpEntity(exception.toString)
              }
              HttpEntity("Fehler beim speichern als XML")
            } else if (body.getContentType() == ContentTypes.`application/json`) {
              val entityFuture: Future[String] = body.toStrict(1.seconds).map(_.data.decodeString("UTF-8"))
              entityFuture.onComplete {
                case Success(value)  =>
                  println("Speichere als Json")
                  controller.save(value, "json")
                  HttpEntity("Speichern als Json erfolgreich")
                case Failure(exception) => HttpEntity(exception.toString)
              }
              HttpEntity("Fehler beim speichern als XML")
            } else {
              HttpEntity("Fehler beim Speichern")
            }
          }
        }
      },
      get {
        path("load") {
          controller.load()
          complete("Request succeded")
        }
      }
    )

  val bindingFuture = Http().newServerAt("localhost", 8081).bind(route)

}