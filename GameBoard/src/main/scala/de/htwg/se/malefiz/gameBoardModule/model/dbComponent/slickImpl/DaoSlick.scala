package de.htwg.se.malefiz.gameBoardModule.model.dbComponent.slickImpl

import de.htwg.se.malefiz.gameBoardModule.GameBoardServer.cellLinksFile
import de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent.ControllerInterface
import de.htwg.se.malefiz.gameBoardModule.model.dbComponent.DaoInterface
import de.htwg.se.malefiz.gameBoardModule.model.gameBoardComponent.GameBoardInterface
import de.htwg.se.malefiz.gameBoardModule.model.gameBoardComponent.gameBoardBaseImpl._
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

class DaoSlick extends DaoInterface {

  val databaseUrl: String = "jdbc:mysql://" + sys.env.getOrElse("DATABASE_HOST", "localhost:3306") + "/" + sys.env
    .getOrElse("MYSQL_DATABASE", "malefizdb") + "?serverTimezone=UTC"
  val databaseUser: String = sys.env.getOrElse("MYSQL_USER", "malefiz")
  val databasePassword: String = sys.env.getOrElse("MYSQL_PASSWORD", "malefiz21")

  val database = Database.forURL(
    url = databaseUrl,
    driver = "com.mysql.cj.jdbc.Driver",
    user = databaseUser,
    password = databasePassword
  )

  val players = TableQuery[PlayersTable]
  val playersTurn = TableQuery[PlayersturnTable]
  val cells = TableQuery[CellsTable]
  val gameStats = TableQuery[GameStatsTable]

  val playersQuery = players.sortBy(_.playerNumber.desc).result
  val cellsQuery = cells.sortBy(_.cellNumber.desc).result
  val playersTurnQuery = playersTurn.result.head
  val dicedNumberQuery = gameStats.map(dice => dice.dicedNumber).result.head
  val selectedFigureQuery = gameStats.map(sf => (sf.playerNumber, sf.figureNumber)).result.head
  val stateNumberQuery = gameStats.map(sn => sn.stateNumber).result.head

  override def read(): Future[Option[GameBoardInterface]] = {

    val composedAction = for {
      players <- cellsQuery
      cells <- playersQuery
      pTurn <- playersTurnQuery
      dice <- dicedNumberQuery
      sFigure <- selectedFigureQuery
      state <- stateNumberQuery
    } yield (players, cells, pTurn, dice, sFigure, state)

    database
      .run(composedAction)
      .map(value => (cellMapper(value._1),
        playerMapper(value._2),
        Option(Player(value._3.playerNumber, value._3.name)),
        Option(value._4),
        Option(value._5),
        Option(value._6))
      )
      .map(value => Option(GameBoard(value._1,
        value._2,
        Creator().getCellGraph(cellLinksFile),
        Set.empty,
        value._4,
        value._3,
        value._5,
        value._6,
        Option.empty))
      )
  }

  def playerMapper(p: Seq[Player]): List[Option[Player]] = {
    var _players: List[Option[Player]] = Nil
    p.foreach(player => {
      _players = Option(Player(player.playerNumber, player.name)) :: _players
    })
    _players
  }

  def cellMapper(cell: Seq[Cell]): List[Cell] = {
    var _cellList: List[Cell] = Nil
    cell.foreach(c => {
      _cellList = Cell(cellNumber = c.cellNumber, playerNumber = c.playerNumber,
        figureNumber = c.figureNumber,
        wallPermission = c.wallPermission,
        hasWall = c.hasWall, coordinates = Point(c.coordinates.x_coordinate, c.coordinates.y_coordinate),
        possibleFigures = c.possibleFigures,
        possibleCells = c.possibleCells) :: _cellList
    })
    _cellList
  }

  def gameBoardMapper(gameBoardInterface: GameBoardInterface): GameStats = {
    GameStats(
      gameBoardInterface.selectedFigure.get._1,
      gameBoardInterface.selectedFigure.get._2,
      gameBoardInterface.dicedNumber.getOrElse(0),
      gameBoardInterface.stateNumber.getOrElse(1))
  }

  override def create(gameBoardInterface: GameBoardInterface, controllerInterface: ControllerInterface): Unit = {
    val injection = DBIO.seq(
      players.schema.dropIfExists, playersTurn.schema.dropIfExists, cells.schema.dropIfExists, gameStats.schema
        .dropIfExists,
      (players.schema ++ playersTurn.schema ++ cells.schema ++ gameStats.schema).createIfNotExists,
      cells ++= (for {
        cell <- gameBoardInterface.cellList
      } yield cell),
      players ++= (for {
        player <- gameBoardInterface.players
        p <- player
      } yield p),
      playersTurn += gameBoardInterface.playersTurn.get,
      gameStats += gameBoardMapper(gameBoardInterface)
    )
    database.run(injection).onComplete {
      case Success(_) => println("LOG: Daten wurden erfolgreich in MySQL gespeichert")
      case Failure(_) => println("LOG: Fehler beim speichern der Daten in MySQL")
    }

  }

  override def update(): Unit = ???

  override def delete(): Unit = ???
}
