package de.htwg.se.malefiz.model.fileIoComponent.fileIoJsonImpl

import java.io.{File, PrintWriter}

import de.htwg.se.malefiz.MalefizModule
import de.htwg.se.malefiz.controller.controllerComponent.ControllerInterface
import de.htwg.se.malefiz.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.malefiz.model.fileIoComponent.FileIOInterface
import de.htwg.se.malefiz.model.gameBoardComponent.GameboardInterface
import de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl.{Cell, Point}
import de.htwg.se.malefiz.model.playerComponent.Player
import com.google.inject.{Guice, Inject}
import net.codingwell.scalaguice.InjectorExtensions._
import play.api.libs.json._

import scala.io.Source

class FileIO @Inject extends FileIOInterface{

  override def loadController: ControllerInterface = {

    val controllerNeu = new Controller(load)
    val source = Source.fromFile("gameboard.json")
    val string = source.getLines.mkString
    source.close()
    val json: JsValue = Json.parse(string)

    implicit val playerReader: Reads[Player] = Json.reads[Player]

    val dicenr: Int = (json \ "diceNumber").as[Int]
    val plTurn: Player = (json \ "playersTurn").as[Player]
    val f1: Int = (json \ "selectedFigure1").as[Int]
    val f2: Int = (json \ "selectedFigure2").as[Int]
    val state: Int = (json \ "gameState").as[Int]

    controllerNeu.setDicedNumber(dicenr)
    controllerNeu.playersTurn = controllerNeu.getPlayer(plTurn.playerNumber-1)
    controllerNeu.setSelectedFigures(f1,f2)
    controllerNeu.setStateNumber(state)

    controllerNeu
  }


  override def load: GameboardInterface = {

    val source = Source.fromFile("gameboard.json")
    val string = source.getLines.mkString
    source.close()
    val json: JsValue = Json.parse(string)

    val injector = Guice.createInjector(new MalefizModule)
    var gameboard: GameboardInterface = injector.instance[GameboardInterface]

    implicit val pointReader: Reads[Point] = Json.reads[Point]
    implicit val cellReader: Reads[Cell] = Json.reads[Cell]
    implicit val playerReader: Reads[Player] = Json.reads[Player]

    val players: List[Player] = (json \ "players").as[List[Player]]
    val posCells: Set[Int] = (json \ "possibleCells").as[Set[Int]]


    var found: Set[Int] = Set[Int]()

    for (index <- 0 until posCells.size) {
      val possCell = (json \ "possibleCells")(index).as[Int]
      gameboard = gameboard.setPosiesCellTrue(List(possCell))
      found += possCell
    }
    gameboard = gameboard.setPossibleCell(found)


    for(index <- 0 until 131) {

      val cellNumber: Int = ((json \ "cells")(index) \ "cellNumber").as[Int]
      val playerNumber: Int = ((json \ "cells")(index) \ "playerNumber").as[Int]
      val figureNumber: Int = ((json \ "cells")(index) \ "figureNumber").as[Int]
      val hasWall: Boolean = ((json \ "cells")(index) \ "hasWall").as[Boolean]

      gameboard = gameboard.setPlayer(playerNumber,cellNumber)
      gameboard = gameboard.setFigure(figureNumber,cellNumber)

      if(hasWall){
        gameboard = gameboard.setWall(cellNumber)
      }
      if(!hasWall){
        gameboard = gameboard.rWall(cellNumber)
      }
    }

    //players.filter(_ != null).map(x => gameboard.createPlayer(x.name))
    for (player <- players){
      if (player != "")
        gameboard = gameboard.createPlayer(player.name)
    }
    gameboard = gameboard.setPossibleCell(posCells)

    gameboard
  }

  implicit val pointWrites: Writes[Point] = (point: Point) => {
    Json.obj(
      "x" -> JsNumber(point.x_coordinate),
      "y" -> JsNumber(point.y_coordinate)
    )
  }


  implicit val playerWrites: Writes[Player] = (player: Player) => {
    Json.obj(
      "playerNumber" ->player.playerNumber,
      "name" -> player.name
    )
  }

  override def save(gameBoard: GameboardInterface, controller: ControllerInterface): Unit = {
    val jsonGameBoard = gameBoardToJson(gameBoard,controller)
    val jsonFile = Json.prettyPrint(jsonGameBoard)

    val pw = new PrintWriter(new File("gameboard.json"))
    pw.write(jsonFile)
    pw.close()
  }

  def gameBoardToJson(gameB: GameboardInterface,controller: ControllerInterface): JsObject = {
    Json.obj(
      "players" -> Json.toJson(
        for {
          p <- gameB.getPlayer
        } yield Json.toJson(p)
      ),
      "playersTurn" -> controller.getPlayersTurn,
      "diceNumber" -> controller.getDicedNumber,
      "selectedFigure1" -> controller.getSelectedFigure._1,
      "selectedFigure2" -> controller.getSelectedFigure._2,
      "gameState" -> controller.getGameState.state.toString.toInt,
      "possibleCells" -> gameB.getPossibleCells,
      "cells" -> Json.toJson(
        for {
          c <- gameB.getCellList
        } yield {
          Json.obj(
            "cellNumber" -> c.cellNumber,
            "playerNumber" -> c.playerNumber,
            "figureNumber" -> c.figureNumber,
            "hasWall" -> c.hasWall,
            "coordinates"-> c.coordinates
          )
        }
      )
    )
  }

}
