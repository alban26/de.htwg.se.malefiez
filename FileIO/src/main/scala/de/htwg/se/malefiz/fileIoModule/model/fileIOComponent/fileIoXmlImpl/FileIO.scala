package de.htwg.se.malefiz.fileIoModule.model.fileIOComponent.fileIoXmlImpl

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpMethods, HttpRequest, HttpResponse}
import de.htwg.se.malefiz.fileIoModule.model.FileIOInterface

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt
import scala.io.Source
import scala.util.control.Breaks.break
import scala.util.{Failure, Success, Try}

class FileIO extends FileIOInterface {
  implicit val system = ActorSystem(Behaviors.empty, "GameBoard")
  implicit val executionContext = system.executionContext

  def load: Unit = {
    val gameString = getXmlString
    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(HttpMethods.POST, uri = "http://localhost:8080/gameBoardXml", entity = gameString))
    responseFuture.onComplete {
      case Success(value) => {
        val entityFuture: Future[String] = value.entity.toStrict(5.seconds).map(_.data.decodeString("UTF-8"))
        entityFuture.onComplete {
          case Success(result) => println(result)
          case Failure(exception) => sys.error(exception.toString)
        }
      }
      case Failure(_) => sys.error(_)
    }
  }

  def getXmlString: String = {
    fileNotFound("FileIO/src/main/scala/de/htwg/se/malefiz/fileIoModule/gameboard.xml") match {
      case Success(v) => println("File Found")
      case Failure(v) => println("File not Found")
        break
    }
    val file = Source.fromFile("FileIO/src/main/scala/de/htwg/se/malefiz/fileIoModule/gameboard.xml")
    try file.mkString finally file.close()
  }

  def fileNotFound(filename: String): Try[String] = {
    Try(Source.fromFile(filename).getLines().mkString)
  }

  def save(gamestate_json: String): Unit = {
    import java.io._
    val print_writer = new PrintWriter(new File("FileIO/src/main/scala/de/htwg/se/malefiz/fileIoModule/gameboard.xml"))
    print_writer.write(gamestate_json)
    print_writer.close()
  }

}

