package de.htwg.se.malefiz.model.fileIoComponent.fileIoJsonImpl

import java.io.{File, PrintWriter}

import com.google.inject.{Guice, Inject}
import de.htwg.se.malefiz.MalefizModule
import de.htwg.se.malefiz.controller.controllerComponent.ControllerInterface
import de.htwg.se.malefiz.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.malefiz.model.fileIoComponent.FileIOInterface
import de.htwg.se.malefiz.model.gameBoardComponent.GameBoardInterface
import de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl.{Cell, Point}
import de.htwg.se.malefiz.model.playerComponent.Player
import net.codingwell.scalaguice.InjectorExtensions._
import play.api.libs.json._

import scala.io.{BufferedSource, Source}

class FileIO @Inject() extends FileIOInterface {

  override def loadController: ControllerInterface = {

    val newController = new Controller(load)
    val gameBoardSource = Source.fromFile("gameboard.json")
    val string = gameBoardSource.getLines.mkString
    gameBoardSource.close()
    val json: JsValue = Json.parse(string)

    implicit val playerReader: Reads[Player] = Json.reads[Player]

    val diceNumber: Int = (json \ "diceNumber").as[Int]
    val playersTurn: Player = (json \ "playersTurn").as[Player]
    val f1: Int = (json \ "selectedFigure1").as[Int]
    val f2: Int = (json \ "selectedFigure2").as[Int]
    val gameState: Int = (json \ "gameState").as[Int]

    newController.setDicedNumber(diceNumber)
    newController.setPlayersTurn(
      Option
        .apply(newController.gameBoard.players(playersTurn.playerNumber - 1))
    )
    newController.setSelectedFigure(f1, f2)
    newController.setStateNumber(gameState)

    newController
  }

  override def load: GameBoardInterface = {

    val source: BufferedSource = {
      Source.fromFile("gameboard.json")
    }
    val string = source.getLines.mkString
    source.close()
    val json: JsValue = Json.parse(string)

    val injector = Guice.createInjector(new MalefizModule)
    var gameBoard: GameBoardInterface = injector.instance[GameBoardInterface]

    implicit val pointReader: Reads[Point] = Json.reads[Point]
    implicit val cellReader: Reads[Cell] = Json.reads[Cell]
    implicit val playerReader: Reads[Player] = Json.reads[Player]

    val players: List[Player] = (json \ "players").as[List[Player]]
    val posCells: Set[Int] = (json \ "possibleCells").as[Set[Int]]

    var found: Set[Int] = Set[Int]()

    for (index <- 0 until posCells.size) {
      val possCell = (json \ "possibleCells")(index).as[Int]
      gameBoard = gameBoard.setPossibleCellsTrueOrFalse(List(possCell), gameBoard.stateNumber.toString)
      found += possCell
    }
    gameBoard = gameBoard.setPossibleCell(found)

    for (index <- 0 until 131) {

      val cellNumber: Int = ((json \ "cells")(index) \ "cellNumber").as[Int]
      val playerNumber: Int = ((json \ "cells")(index) \ "playerNumber").as[Int]
      val figureNumber: Int = ((json \ "cells")(index) \ "figureNumber").as[Int]
      val hasWall: Boolean = ((json \ "cells")(index) \ "hasWall").as[Boolean]

      gameBoard = gameBoard setPlayer(playerNumber, cellNumber)
      gameBoard = gameBoard setFigure(figureNumber, cellNumber)

      if (hasWall)
        gameBoard = gameBoard.setWall(cellNumber)
      if (!hasWall)
        gameBoard = gameBoard.removeWall(cellNumber)
    }

    //players.filter(_.name != "").map(x => gameboard.createPlayer(x.name))

    players.foreach(player => if (player.name != "") gameBoard = gameBoard.createPlayer(player.name))

    /*
    for (player <- players)
      if (player != "")
        gameboard = gameboard.createPlayer(player.name)
    */

    gameBoard = gameBoard.setPossibleCell(posCells)
    gameBoard

  }

  implicit val pointWrites: Writes[Point] = (point: Point) => {
    Json.obj(
      "x" -> JsNumber(point.x_coordinate),
      "y" -> JsNumber(point.y_coordinate)
    )
  }

  implicit val playerWrites: Writes[Player] = (player: Player) => {
    Json.obj(
      "playerNumber" -> player.playerNumber,
      "name" -> player.name
    )
  }

  override def save(
      gameBoard: GameBoardInterface,
      controller: ControllerInterface
  ): Unit = {
    val jsonGameBoard = gameBoardToJson(gameBoard, controller)
    val jsonFile = Json.prettyPrint(jsonGameBoard)

    val pw = new PrintWriter(new File("gameboard.json"))
    pw.write(jsonFile)
    pw.close()
  }

  def gameBoardToJson(
      gameBoard: GameBoardInterface,
      controller: ControllerInterface
  ): JsObject =
    Json.obj(
      "players" -> Json.toJson(
        for {
          p <- gameBoard.players
        } yield Json.toJson(p)
      ),
      "playersTurn" -> controller.gameBoard.playersTurn,
      "diceNumber" -> controller.gameBoard.dicedNumber,
      "selectedFigure1" -> controller.gameBoard.selectedFigure.get._1,
      "selectedFigure2" -> controller.gameBoard.selectedFigure.get._2,
      "gameState" -> controller.getGameState.currentState.toString.toInt,
      "possibleCells" -> gameBoard.possibleCells,
      "cells" -> Json.toJson(
        for {
          c <- gameBoard.cellList
        } yield Json.obj(
          "cellNumber" -> c.cellNumber,
          "playerNumber" -> c.playerNumber,
          "figureNumber" -> c.figureNumber,
          "hasWall" -> c.hasWall,
          "coordinates" -> c.coordinates
        )
      )
    )

}
