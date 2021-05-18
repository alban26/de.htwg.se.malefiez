package de.htwg.se.malefiz.gameBoardModule.rest.restComponent.restBaseImpl

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.util.ByteString
import de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent.ControllerInterface
import de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.malefiz.gameBoardModule.model.gameBoardComponent.GameBoardInterface
import de.htwg.se.malefiz.gameBoardModule.model.gameBoardComponent.gameBoardBaseImpl.{Cell, Player, Point}
import de.htwg.se.malefiz.gameBoardModule.rest.restComponent.RestControllerInterface
import play.api.libs.json.{JsNumber, JsObject, Json, Writes}

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt
import scala.util.{Failure, Success}
import scala.xml.Utility.{sequenceToXML, serialize, toXML}
import scala.xml.{Elem, PrettyPrinter, XML}

class RestController extends RestControllerInterface {

  implicit val system = ActorSystem(Behaviors.empty, "Malefiz")
  implicit val executionContext = system.executionContext

  override def saveAsJson(gameBoard: GameBoardInterface, controller: Controller): Unit = {
    val jsonGameBoard = gameBoardToJson(gameBoard, controller)
    val jsonFile = Json.prettyPrint(jsonGameBoard)

    val responseFuture: Future[HttpResponse] =
      Http().singleRequest(HttpRequest(method = HttpMethods.POST,
        uri = "http://fileio:8081/save",
        entity = HttpEntity(ContentTypes.`application/json`, jsonFile)))
    responseFuture.onComplete {
      case Success(value) =>
        val entityFuture: Future[String] = value.entity.toStrict(5.seconds).map(_.data.decodeString("UTF-8"))
        entityFuture.onComplete {
          case Success(value) => println("Spiel wurde erfolgreich gespeichert: " + value)
          case Failure(exception) => println("Fehler beim Speichern: " + exception)
        }
      case Failure(exception) => println(" Fehler beim Speichern: " + exception)
    }
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

  def gameBoardToJson(gameBoard: GameBoardInterface, controller: ControllerInterface): JsObject = Json.obj(
    "players" -> Json.toJson(
      for {
        p <- gameBoard.players
      } yield Json.toJson(p)
    ),
    "playersTurn" -> controller.gameBoard.playersTurn,
    "diceNumber" -> controller.gameBoard.dicedNumber,
    "selectedFigure1" -> controller.gameBoard.selectedFigure.getOrElse((1, 1))._1,
    "selectedFigure2" -> controller.gameBoard.selectedFigure.getOrElse((1, 1))._2,
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

  override def saveAsXML(gameBoard: GameBoardInterface, controller: Controller): Unit = {

    val x = gameBoardToXml(gameBoard, controller)
    val prettyPrinter = new PrettyPrinter(80, 2)
    val y = prettyPrinter.format(x)
    val responseFuture: Future[HttpResponse] =
      Http().singleRequest(HttpRequest(method = HttpMethods.POST,
        uri = "http://fileio:8081/save",
        entity = HttpEntity(ContentTypes.`text/xml(UTF-8)`, y)))
    responseFuture.onComplete {
      case Success(value) =>
        val entityFuture: Future[String] = value.entity.toStrict(5.seconds).map(_.data.decodeString("UTF-8"))
        entityFuture.onComplete {
          case Success(value) => println("Spiel wurde erfolgreich gespeichert: " + value)
          case Failure(exception) => println("Fehler beim Speichern: " + exception)
        }
      case Failure(exception) => println(" Fehler beim Speichern: " + exception)
    }
  }

  def gameBoardToXml(gameBoard: GameBoardInterface, controller: ControllerInterface): Elem =
    <gameboard size={gameBoard.players.size.toString}>
      {
      for {
        l1 <- gameBoard.cellList.indices
      } yield cellToXml(l1, gameBoard.cellList(l1))
      }
      <player>
        {
        for {
          l1 <- gameBoard.players.indices
        } yield playerToXml(l1, gameBoard.players(l1).get)
        }
      </player>
      <possibleCells>
        {
        for {
          l1 <- gameBoard.possibleCells
        } yield possibleCellsToXml(l1)
        }
      </possibleCells>
      <playersTurn turnZ={controller.gameBoard.playersTurn.get.playerNumber.toString} turnN ={
      controller.gameBoard.playersTurn.get.name
      }></playersTurn>

      <dicedNumber number={controller.gameBoard.dicedNumber.toString}></dicedNumber>

      <selectedFigure sPlayer={controller.gameBoard.selectedFigure.get._1.toString} sFigure={
      controller.gameBoard.selectedFigure.get._2.toString
      } ></selectedFigure>

      <gameState state={controller.getGameState.currentState.toString}></gameState>

    </gameboard>

  def possibleCellsToXml(l1: Int): Elem =
    <pCells posCell={l1.toString}>
      {l1}
    </pCells>
  /*
 def gameStateToXml(state: GameState): Elem = {
   <gameState state={state.state.toString}>
     {state}
   </gameState>
 }
 dplayersTurnToXml(player: Player): Elem = {
   <playersTurn playersturnname ={player.name}>
     {player}
   </playersTurn>
 }
   */
  def playerToXml(i: Int, player: Player): Elem =
    <player playernumber={player.playerNumber.toString} playername={player.name}>
      {player}
    </player>

  def cellToXml(l1: Int, cell: Cell): Elem =
    <cell cellnumber={cell.cellNumber.toString} playernumber={cell.playerNumber.toString}
          figurenumber={cell.figureNumber.toString} haswall={cell.hasWall.toString}>
      {cell}
    </cell>
}
