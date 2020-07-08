package de.htwg.se.malefiz.model.fileIoComponent.fileIoJsonImpl


import java.io.{File, PrintWriter, Reader}

import de.htwg.se.malefiz.Malefiz.{cellLinksFile, injector}
import de.htwg.se.malefiz.controller.controllerComponent.ControllerInterface
import de.htwg.se.malefiz.model.fileIoComponent.FileIOInterface
import de.htwg.se.malefiz.model.gameBoardComponent.GameboardInterface
import de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl.{Cell, Creator, GameBoard, Point}
import de.htwg.se.malefiz.model.playerComponent.Player
import play.api.libs.json._

import scala.io.Source


class FileIO extends FileIOInterface{

  val controller: ControllerInterface = injector.getInstance(classOf[ControllerInterface])

  override def load: GameboardInterface = {

    val source = Source.fromFile("gameboard.json")
    val string = source.getLines.mkString
    source.close()
    val json: JsValue = Json.parse(string)

    implicit val pointReader: Reads[Point] = Json.reads[Point]
    implicit val cellReader: Reads[Cell] = Json.reads[Cell]
    implicit val playerReader: Reads[Player] = Json.reads[Player]

    val cells : List[Cell] = (json \ "cells").as[List[Cell]]
    val players: List[Player] = (json \ "players").as[List[Player]]
    val posCells: Set[Int] = (json \ "possibleCells").as[Set[Int]]

    GameBoard(cells, players, Creator().getCellGraph(cellLinksFile), posCells)
  }

  implicit val pointWrites: Writes[Point] = (point: Point) => {
    Json.obj(
      "x" -> point.x_coordinate,
      "y" -> point.y_coordinate
    )
  }


  implicit val playerWrites: Writes[Player] = (player: Player) => {
    Json.obj(
      "playernr" -> player.playerNumber,
      "playername" -> player.name
    )
  }

  implicit val cellWrites: Writes[Cell] = (cell: Cell) => {
    Json.obj(
      "cellnr" -> cell.cellNumber,
      "playernr" -> cell.playerNumber,
      "figurenr" -> cell.figureNumber,
      "wallPermission" -> cell.wallPermission,
      "hasWall" -> cell.hasWall,
      "coordinates" -> Json.toJson(cell.coordinates),
      "posFigure" -> cell.possibleFigures,
      "posCell" -> cell.possibleCells
    )
  }

  override def save(gameBoard: GameboardInterface, controller: ControllerInterface): Unit = {
    val jsonGameBoard = gameBoardToJson(gameBoard)
    val jsonFile = Json.prettyPrint(jsonGameBoard)

    val pw = new PrintWriter(new File("gameboard.json"))
    pw.write(jsonFile)
    pw.close()
  }

  def gameBoardToJson(gameB: GameboardInterface): JsObject = {
    Json.obj(
      "players" -> Json.toJson(
        for {
          p <- gameB.getPlayer
        } yield Json.toJson(p)
      ),
      "gameState" -> controller.getGameState.toString,
      "possibleCells" -> gameB.getPossibleCells,
      "cells" -> Json.toJson(
        for {
          c <- gameB.getCellList
        } yield Json.toJson(c)
      )
    )
  }

  //override def loadController: ControllerInterface = ???
}
