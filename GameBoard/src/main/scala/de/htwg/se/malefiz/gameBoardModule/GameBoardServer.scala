package de.htwg.se.malefiz.gameBoardModule
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.server.Directives._
import com.google.inject.{Guice, Injector}
import de.htwg.se.malefiz.gameBoardModule.aview.Tui
import de.htwg.se.malefiz.gameBoardModule.aview.gui.SwingGui
import de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent.ControllerInterface
import spray.json.DefaultJsonProtocol

import scala.io.StdIn

object GameBoardServer extends SprayJsonSupport with DefaultJsonProtocol {

  val cellConfigFile = "/configuration/mainCellConfiguration"
  val cellLinksFile = "/configuration/mainCellLinks"

  val injector: Injector = Guice.createInjector(new GameBoardServerModule)
  val controller: ControllerInterface = injector.getInstance(classOf[ControllerInterface])
  val tui = new Tui(controller)
  val swingGui = new SwingGui(controller)

  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem(Behaviors.empty, "GameBoard")
    implicit val executionContext = system.executionContext


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
          playerList.foreach(player => tui.processInput("n " + player))
          complete(controller.gameBoard.players.toString())
        }
      },
      path("newGame") {
        get {
          tui.processInput("n start")
          openGameBoardGui()
          complete(HttpEntity("Spiel wurde gestartet"))
        }
      },
      path("loadGame") {
        get {
          tui.processInput("load")
          complete(HttpEntity("Spiel wird nun geladen"))
        }
      },
      path("gameBoard") {
        get {
          openGameBoardGui()
          complete(HttpEntity("Spiel wurde geöffnet"))
        }
      },
      (path("gameBoardJson") & post) {
        entity(as[String]) { gameJsonString => //
          controller.evalJson(gameJsonString)
          complete(HttpEntity("Laden von Json war erfolgreich!"))
        }
      },
      (path("gameBoardXml") & post) {
        entity(as[String]) { gameXmlString => //
          controller.evalXml(gameXmlString)
          complete(HttpEntity("Laden von Xml war erfolgreich!"))
        }
      }
    )

    val bindingFuture = Http().newServerAt("0.0.0.0", 8083).bind(route)

    println(s" GameBoard Server online at http://0.0.0.0:8081/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }

}