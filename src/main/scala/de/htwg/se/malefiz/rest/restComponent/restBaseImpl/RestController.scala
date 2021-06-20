package de.htwg.se.malefiz.rest.restComponent.restBaseImpl

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import de.htwg.se.malefiz.Malefiz.controller
import de.htwg.se.malefiz.rest.restComponent.RestControllerInterface
import spray.json.DefaultJsonProtocol.{StringJsonFormat, listFormat}
import spray.json.enrichAny

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt
import scala.util.{Failure, Success}

class RestController extends RestControllerInterface {

  implicit val system = ActorSystem(Behaviors.empty, "Malefiz")
  implicit val executionContext = system.executionContext

  override def startGameRequest(): Unit = {
    val startGameRequest = HttpRequest(method = HttpMethods.GET, uri = "http://localhost:8083/newGame")
    val responseFuture: Future[HttpResponse] = Http().singleRequest(startGameRequest)
    responseFuture.onComplete {
      case Success(value) =>
        val entityFuture: Future[String] = value.entity.toStrict(5.seconds).map(_.data.decodeString("UTF-8"))
        entityFuture.onComplete {
          case Success(value) => println("Neues Spiel wurde im GameBoardService gestartet: " + value)
          case Failure(exception) => println("Fehler beim Starten des Spiels im GameboardService: ", exception)
        }
      case Failure(exception) => println("Fehler beim Starten des Spiels im GameBoardService: " + exception)
    }
  }

  override def sendPlayerListRequest(playerList: List[String]): Unit = {
    println("Sende Spielerliste an Gamboard: " + playerList.toString())
    val sendPlayersRequest = HttpRequest(method = HttpMethods.POST,
      uri = "http://localhost:8083/players",
      entity = HttpEntity(ContentTypes.`application/json`, playerList.toJson.toString())
    )
    val responseFuture: Future[HttpResponse] = Http().singleRequest(sendPlayersRequest)
    responseFuture.onComplete {
      case Success(value) =>
        val entityFuture: Future[String] = value.entity.toStrict(5.seconds).map(_.data.decodeString("UTF-8"))
        entityFuture.onComplete {
          case Success(value) => startGameRequest()
          case Failure(exception) => controller.showErrorMessage(exception.toString)
        }
      case Failure(exception) => controller.showErrorMessage(exception.toString)
    }

  }

  override def sendLoadRequest(): Unit = {
    val loadGameRequest = HttpRequest(method = HttpMethods.GET, uri = "http://localhost:8083/loadGame")
    val responseFuture: Future[HttpResponse] = Http().singleRequest(loadGameRequest)
    responseFuture.onComplete {
      case Success(value) =>
        val entityFuture: Future[String] = value.entity.toStrict(1.seconds).map(_.data.decodeString("UTF-8"))
        entityFuture.onComplete {
          case Success(value) =>
            println("Spiel wird geladen...")
            val deadline = 2.seconds.fromNow
            while (deadline.hasTimeLeft) {}
            openGameBoardRequest()
          case Failure(exception) => controller.showErrorMessage(exception.toString)
        }
      case Failure(exception) => controller.showErrorMessage(exception.toString)
    }
  }

  override def openGameBoardRequest(): Unit = {
    val openGameBoardRequest = HttpRequest(method = HttpMethods.GET, uri = "http://localhost:8083/gameBoard")
    val responseFuture: Future[HttpResponse] = Http().singleRequest(openGameBoardRequest)
    responseFuture.onComplete {
      case Success(value) =>
        val entityFuture: Future[String] = value.entity.toStrict(5.seconds).map(_.data.decodeString("UTF-8"))
        entityFuture.onComplete {
          case Success(value) => println("Spielbrett wird angezeigt: " + value)
          case Failure(exception) => controller.showErrorMessage(exception.toString)
        }
      case Failure(exception) => controller.showErrorMessage(exception.toString)
    }
  }

  override def sendLoadFromDBRequest(): Unit = {
    val loadGameRequest = HttpRequest(method = HttpMethods.GET, uri = "http://localhost:8083/loadGameFromDB")
    val responseFuture: Future[HttpResponse] = Http().singleRequest(loadGameRequest)
    responseFuture.onComplete {
      case Success(value) =>
        val entityFuture: Future[String] = value.entity.toStrict(1.seconds).map(_.data.decodeString("UTF-8"))
        entityFuture.onComplete {
          case Success(value) =>
            println("Spiel wird geladen...")
            val deadline = 2.seconds.fromNow
            while (deadline.hasTimeLeft) {}
            openGameBoardRequest()
          case Failure(exception) => controller.showErrorMessage(exception.toString)
        }
      case Failure(exception) => controller.showErrorMessage(exception.toString)
    }
  }
}
