package de.htwg.se.malefiz.gameBoardModule.model.dbComponent.mongoDbImpl

import de.htwg.se.malefiz.gameBoardModule.GameBoardServer.controller
import de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent.ControllerInterface
import de.htwg.se.malefiz.gameBoardModule.model.dbComponent.DaoInterface
import de.htwg.se.malefiz.gameBoardModule.model.gameBoardComponent.GameBoardInterface
import de.htwg.se.malefiz.gameBoardModule.rest.restComponent.restBaseImpl.RestController
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase, _}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}


class DaoMongoDB extends DaoInterface {

  val uri: String = "mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&ssl=false"

  val client: MongoClient = MongoClient(uri)
  val database: MongoDatabase = client.getDatabase("malefiz")
  val gameBoardCollection: MongoCollection[Document] = database.getCollection("malefiz")

  override def read(): Future[Option[GameBoardInterface]] = {
    gameBoardCollection
      .find()
      .first()
      .toFuture()
      .map(document => document.getString("gameBoard"))
      .map(json => Option(controller.loadGameBoardJson(json)))
  }

  override def create(gameBoardInterface: GameBoardInterface, controllerInterface: ControllerInterface): Unit = {

    val rc = new RestController
    val gameBoardJsonString = rc.gameBoardToJson(gameBoardInterface, controllerInterface).toString()

    val gameBoardDoc: Document = Document("gameBoard" -> gameBoardJsonString)
    gameBoardCollection.insertOne(gameBoardDoc).toFuture().onComplete {
      case Success(_) => print("LOG>> Erfolgreich gespeichert")
      case Failure(_) => print("LOG>> Fehler beim abspeichern")
    }
  }

  override def update(): Unit = ???

  override def delete(): Unit = ???
}
