package de.htwg.se.malefiz.gameBoardModule
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.server.Directives._
import com.google.inject.{Guice, Injector}
import com.typesafe.scalalogging.Logger
import de.htwg.se.malefiz.gameBoardModule.aview.Tui
import de.htwg.se.malefiz.gameBoardModule.aview.gui.SwingGui
import de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent.ControllerInterface
import spray.json.DefaultJsonProtocol

import scala.io.StdIn

case class Player(playerNumber: Int, playerName: String)

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {

  //implicit val playerFormat: RootJsonFormat[Player] = jsonFormat2(Player)

}

object GameBoardServer extends JsonSupport {

  val cellConfigFile = "GameBoard/src/main/scala/de/htwg/se/malefiz/gameBoardModule/mainCellConfiguration"
  val cellLinksFile = "GameBoard/src/main/scala/de/htwg/se/malefiz/gameBoardModule/mainCellLinks"

  val injector: Injector = Guice.createInjector(new GameBoardServerModule)
  val controller: ControllerInterface = injector.getInstance(classOf[ControllerInterface])
  val tui = new Tui(controller)
  val swingGui = new SwingGui(controller)

  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem(Behaviors.empty, "GameBoard")
    implicit val executionContext = system.executionContext

    val logger = Logger("GameBoard-Logger")

    def openGameBoardGui(): Unit = {
      swingGui.visible = true
      swingGui.drawGameBoard()
      swingGui.updatePlayerArea()
      swingGui.updatePlayerTurn()
      swingGui.updateInformationArea()
    }

    val route = concat(
      (path("players") & post) {
        entity(as[List[String]]) { playerList => //
          logger.info("Erhalte Spielerliste: {} vom Root-Service", playerList)
          playerList.foreach(player => tui.processInput("n " + player))
          complete(controller.gameBoard.players.toString())
        }
      },
      path("newGame") {
        get {
          logger.info("Starte neues Spiel")
          tui.processInput("n start")
          openGameBoardGui()
          complete(HttpEntity("Spiel wurde gestartet"))
        }
      },
      path("loadGame") {
        get {
          logger.info("Spiel wird geladen")
          tui.processInput("load")
          complete(HttpEntity("Spiel wurde geladen"))
        }
      },
      path("gameBoard") {
        get {
          logger.info("Swingui wird gestartet")
          openGameBoardGui()
          complete(HttpEntity("Spiel wurde geöffnet"))
        }
      }
    )



    val bindingFuture = Http().newServerAt("localhost", 8080).bind(route)

    println(s" GameBoard Server online at http://localhost:8081/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}