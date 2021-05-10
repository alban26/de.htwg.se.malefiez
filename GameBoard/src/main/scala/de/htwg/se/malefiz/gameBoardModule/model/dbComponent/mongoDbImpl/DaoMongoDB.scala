package de.htwg.se.malefiz.gameBoardModule.model.dbComponent.mongoDbImpl

import de.htwg.se.malefiz.gameBoardModule.GameBoardServer.controller
import de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent.ControllerInterface
import de.htwg.se.malefiz.gameBoardModule.model.dbComponent.DaoInterface
import de.htwg.se.malefiz.gameBoardModule.model.gameBoardComponent.GameBoardInterface
import de.htwg.se.malefiz.gameBoardModule.rest.restComponent.restBaseImpl.RestController
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}
import org.mongodb.scala._
import org.mongodb.scala.result.InsertOneResult

class DaoMongoDB extends DaoInterface {

  val uri: String = "mongodb://192.168.0.17:27017/?readPreference=primary&authSource=malefiz-mongodb&appname=MongoDB%20Compass&ssl=false"

  val client: MongoClient = MongoClient(uri)

  val database: MongoDatabase = client.getDatabase("malefiz")

  val gameBoardCollection: MongoCollection[Document] = database.getCollection("malefiz")


  override def load(): GameBoardInterface = {
    var waitOnRes = true
    var res: String = ""
    val observable: Observable[Document] = gameBoardCollection.find().first()

    observable.subscribe(new Observer[Document] {
      override def onNext(result: Document): Unit = {
        res = result.getString("gameBoard")
      }

      override def onError(e: Throwable): Unit = println("failed to load data")

      override def onComplete(): Unit = {
        waitOnRes = false
        println("completed loading data")
      }
    })

    while (waitOnRes)
      Thread.sleep(10)
    controller.loadGameBoardJson(res)
  }

  override def save(gameBoardInterface: GameBoardInterface, controllerInterface: ControllerInterface): Unit = {


    val rc = new RestController
    val gameBoardJsonString = rc.gameBoardToJson(gameBoardInterface, controllerInterface).toString()

    val gameBoardDoc: Document = Document("gameBoard" -> gameBoardJsonString)
    gameBoardCollection.insertOne(gameBoardDoc)

    val insertObservable: SingleObservable[InsertOneResult] = gameBoardCollection.insertOne(gameBoardDoc)
    insertObservable.subscribe(new Observer[InsertOneResult] {
      override def onNext(result: InsertOneResult): Unit = println(s"inserted: $result")

      override def onError(e: Throwable): Unit = println(s"failed: $e")

      override def onComplete(): Unit = println("completed")
    })

  }
}
