package de.htwg.se.malefiz.controller.controllerComponent.controllerBaseImpl

import com.google.inject.{Guice, Inject, Injector}
import de.htwg.se.malefiz.Malefiz.entryGui
import de.htwg.se.malefiz.MalefizModule
import de.htwg.se.malefiz.controller.controllerComponent.ControllerInterface
import de.htwg.se.malefiz.rest.restComponent.RestControllerInterface
import de.htwg.se.malefiz.rest.restComponent.restBaseImpl.RestController
import net.codingwell.scalaguice.InjectorExtensions._

class Controller @Inject() extends ControllerInterface {

  val injector: Injector = Guice.createInjector(new MalefizModule)
  val rest: RestControllerInterface = injector.instance[RestController]

  override def checkInput(input: String): Either[String, String] = {
    if (input.split(" ").toList.size >= 5)
      Left("Bitte maximal 4 Spieler eintippen (leerzeichen-getrennt)!")
    else
      Right(input)
  }

  override def execute(input: String): Boolean = {
    sendPlayersToGameService(input.split(" ").toList)
    true
  }

  def startGame(): Boolean = {
    rest.startGameRequest()
    return true
  }

  def sendPlayersToGameService(playerList: List[String]): Boolean = {
    rest.sendPlayerListRequest(playerList)
    true
  }

  override def load(): Boolean = {
    rest.sendLoadRequest()
    true
  }

  override def loadFromDB(): Boolean = {
    rest.sendLoadFromDBRequest()
    true
  }

  override def showErrorMessage(toString: String): Unit = entryGui.showErrorMessage(toString)
}