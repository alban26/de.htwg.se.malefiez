package de.htwg.se.malefiz.model.fileIoComponent.fileIoJsonImpl


import java.io.{File, PrintWriter, Reader}

import com.google.inject.Guice
import de.htwg.se.malefiz.Malefiz.{cellLinksFile, injector}
import de.htwg.se.malefiz.MalefizModule
import de.htwg.se.malefiz.controller.controllerComponent.ControllerInterface
import de.htwg.se.malefiz.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.malefiz.model.fileIoComponent.FileIOInterface
import de.htwg.se.malefiz.model.gameBoardComponent.GameboardInterface
import de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl.{Cell, Creator, GameBoard, Point}
import de.htwg.se.malefiz.model.playerComponent.Player
import play.api.libs.json._
import com.google.inject.Guice
import com.google.inject.name.Names
import net.codingwell.scalaguice.InjectorExtensions._
import play.api.libs.json
import play.api.libs.json._

import scala.io.Source


class FileIO extends FileIOInterface{

  //val controller: ControllerInterface = injector.getInstance(classOf[ControllerInterface])





  override def loadController: ControllerInterface = {

    val controllerNeu = new Controller(load)

    val source = Source.fromFile("gameboard.json")
    val string = source.getLines.mkString
    //source.close()
    val json: JsValue = Json.parse(string)
    implicit val pointReader: Reads[Point] = Json.reads[Point]
    implicit val cellReader: Reads[Cell] = Json.reads[Cell]
    implicit val playerReader: Reads[Player] = Json.reads[Player]


    val dicenr: Int = (json \ "diceNumber").as[Int]
    val plTurn: Player = (json \ "playerTurn").as[Player]
    val f1: Int = (json \ "selectedFigure1").as[Int]
    val f2: Int = (json \ "selectedFigure2").as[Int]
    val state: Int = (json \ "gameState").as[Int]

    println("dicenr "+dicenr)
    println("playturn "+plTurn)
    println("f1 "+f1)
    println("f2 "+f2)
    println("state "+f2)

    controllerNeu.setDicedNumber(dicenr)
    controllerNeu.playersTurn = controllerNeu.getPlayer(plTurn.playerNumber-1)

    controllerNeu.setSelectedFigures(f1,f2)

    controllerNeu.setStateNumber(state)

    controllerNeu
  }


  override def load: GameboardInterface = {

    val source = Source.fromFile("gameboard.json")
    val string = source.getLines.mkString
    //source.close()
    val json: JsValue = Json.parse(string)

    val injector = Guice.createInjector(new MalefizModule)
    var gameboard: GameboardInterface = injector.instance[GameboardInterface]

    implicit val pointReader: Reads[Point] = Json.reads[Point]
    implicit val cellReader: Reads[Cell] = Json.reads[Cell]
    implicit val playerReader: Reads[Player] = Json.reads[Player]
    println("Hab ich notiert")
    val cells : List[Cell] = (json \ "cells").as[List[Cell]]

    val players: List[Player] = (json \ "players").as[List[Player]]
    val posCells: Set[Int] = (json \ "possibleCells").as[Set[Int]]



    for(cell <- cells) {
      val cellNumber: Int = cell.cellNumber
      val playerNumber: Int = cell.playerNumber
      val figureNumber: Int = cell.figureNumber
      val hasWall: Boolean = cell.hasWall

      gameboard = gameboard.setPlayer(playerNumber,cellNumber)
      gameboard = gameboard.setFigure(figureNumber,cellNumber)

      if(hasWall){
        gameboard = gameboard.setWall(cellNumber)
      }
      if(!hasWall){
        gameboard = gameboard.rWall(cellNumber)
      }
    }

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
      "playernr" ->JsNumber(player.playerNumber),
      "playername" -> player.name
    )
  }

  implicit val cellWrites: Writes[Cell] = (cell: Cell) => {
    Json.obj(
      "cellnr" -> JsNumber(cell.cellNumber),
      "playernr" -> JsNumber(cell.playerNumber),
      "figurenr" -> JsNumber(cell.figureNumber),
      "hasWall" -> cell.hasWall,
      "posCell" -> cell.possibleCells
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
      "diceNumber" -> controller.getDicedNumber.toString,
      "selectedFigure1" -> controller.getSelectedFigure._1,
      "selectedFigure2" -> controller.getSelectedFigure._2,
      "gameState" -> controller.getGameState.state.toString,
      "possibleCells" -> gameB.getPossibleCells,
      "cells" -> Json.toJson(
        for {
          c <- gameB.getCellList
        } yield Json.toJson(c)
      )
    )
  }


}
