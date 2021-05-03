package de.htwg.se.malefiz.gameBoardModule.model.gameBoardComponent.gameBoardBaseImpl.dao.h2DB

import de.htwg.se.malefiz.gameBoardModule.model.gameBoardComponent.GameBoardInterface
import de.htwg.se.malefiz.gameBoardModule.model.gameBoardComponent.gameBoardBaseImpl.Player
import de.htwg.se.malefiz.gameBoardModule.model.gameBoardComponent.gameBoardBaseImpl.dao.{DaoInterface, DatabaseSchema}
import slick.dbio.DBIO
import slick.jdbc.H2Profile.api._
import slick.lifted.TableQuery
import com.google.inject.{Guice, Inject, Injector}
import slick.jdbc.meta.MTable

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}

class RelationalDB extends DaoInterface with DatabaseSchema {

  val db = Database.forConfig("h2")
  val a = TableQuery[Cells]


  override def load(): GameBoardInterface = ???

  override def save(gameBoardInterface: GameBoardInterface): Future[Unit] = {
    val future = createSchemaIfNotExists()
    Await.ready(future, Duration.Inf)

    val setup = DBIO.seq(

      players += Player(1, "Robert")
      /*
    val inserts = for (user <- gameBoardInterface.players.toSeq) yield {
      val userRow = user.get
      players += userRow
      }
    players ++= inserts

       */
    )


    db.run(setup).andThen {
      case Success(_) => println("Initial data inserted")
      case Failure(e) => println(s"Initial data not inserted: ${e.getMessage}")
    }


  }

  def createSchemaIfNotExists(): Future[Unit] = {
    db.run(MTable.getTables).flatMap {
      case tables if tables.isEmpty =>
        val schema = players.schema ++ cells.schema
        db.run(schema.create).andThen {
          case Success(_) => println("Schema created")
        }
      case tables if tables.nonEmpty =>
        println("Schema already exists")
        Future.successful()
    }
  }
}
