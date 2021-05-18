package de.htwg.se.malefiz.gameBoardModule.model.dbComponent.slickImpl

import de.htwg.se.malefiz.gameBoardModule.GameBoardServer.cellLinksFile
import de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent.ControllerInterface
import de.htwg.se.malefiz.gameBoardModule.model.dbComponent.DaoInterface
import de.htwg.se.malefiz.gameBoardModule.model.gameBoardComponent.GameBoardInterface
import de.htwg.se.malefiz.gameBoardModule.model.gameBoardComponent.gameBoardBaseImpl._
import slick.jdbc.JdbcBackend.Database
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
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


  override def read(): GameBoardInterface = {

    var _players: List[Option[Player]] = Nil
    val playersQuery = players.sortBy(_.playerNumber.desc).result
    val result_Player = Await.result(database.run(playersQuery), Duration.Inf)
    result_Player.foreach(p => {_players = Option(Player(p.playerNumber, p.name)) :: _players})

    var _cellList: List[Cell] = Nil
    val cellsQuery = cells.sortBy(_.cellNumber.desc).result
    val result_Cells = Await.result(database.run(cellsQuery), Duration.Inf)
    result_Cells.foreach(c => {_cellList = Cell(cellNumber = c.cellNumber, playerNumber = c.playerNumber,
      figureNumber = c.figureNumber,
      wallPermission = c.wallPermission,
      hasWall = c.hasWall, coordinates = Point(c.coordinates.x_coordinate, c.coordinates.y_coordinate),
      possibleFigures = c.possibleFigures,
      possibleCells = c.possibleCells) :: _cellList})

    val playersTurnQuery = playersTurn.result.head
    val result_pTurn = Await.result(database.run(playersTurnQuery), Duration.Inf)
    val _playersTurn = Option(Player(result_pTurn.playerNumber, result_pTurn.name))

    val dicedNumberQuery = gameStats.map(dice => dice.dicedNumber).result.head
    val result_dicedNumber = Await.result(database.run(dicedNumberQuery), Duration.Inf)
    val _dicedNumber = Option(result_dicedNumber)

    val selectedFigureQuery = gameStats.map(sf => (sf.playerNumber, sf.figureNumber)).result.head
    val result_selectedFigure = Await.result(database.run(selectedFigureQuery), Duration.Inf)
    val _selectedFigure = Option(result_selectedFigure)

    val stateNumberQuery = gameStats.map(sn => sn.stateNumber).result.head
    val result_stateNumber = Await.result(database.run(stateNumberQuery), Duration.Inf)
    val _stateNumber = Option(result_stateNumber)

    GameBoard(cellList = _cellList,
      players = _players,
      gameBoardGraph = Creator().getCellGraph(cellLinksFile),
      possibleCells = Set.empty,
      dicedNumber = _dicedNumber,
      playersTurn = _playersTurn,
      selectedFigure = _selectedFigure,
      stateNumber = _stateNumber,
      statementStatus = Option.empty)
  }

  def gameBoardMapper(gameBoardInterface: GameBoardInterface): GameStats = {
    GameStats(
      gameBoardInterface.selectedFigure.get._1,
      gameBoardInterface.selectedFigure.get._2,
      gameBoardInterface.dicedNumber.getOrElse(0),
      gameBoardInterface.stateNumber.getOrElse(1))
  }

  override def create(gameBoardInterface: GameBoardInterface, controllerInterface: ControllerInterface): Unit = {
    println("Ich bin MySQL !!!")
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

    database.run(injection)
  }

  override def update(): Unit = ???

  override def delete(): Unit = ???
}
