package de.htwg.se.malefiz.fileIoModule.controller.controllerComponent.controllerBaseImpl

import com.google.inject.{Guice, Inject, Injector}
import de.htwg.se.malefiz.fileIoModule.FileIOServerModule
import de.htwg.se.malefiz.fileIoModule.controller.controllerComponent.ControllerInterface
import net.codingwell.scalaguice.InjectorExtensions._

import scala.swing.Publisher

class Controller @Inject() extends ControllerInterface with Publisher {

  val injector: Injector = Guice.createInjector(new FileIOServerModule)

  override def execute(input: String): Unit = {
    sendPlayersToGameService(input.split(" ").toList)

  }

  def sendPlayersToGameService(playerList: List[String]): Unit = rest.sendPlayerListRequest(playerList)

  def startGame(): Unit = rest.startGameRequest()

  override def load(): Unit = rest.sendLoadRequest()

}