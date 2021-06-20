package de.htwg.se.malefiz.controller.controllerComponent

import scala.swing.Publisher

trait ControllerInterface extends Publisher {

  def checkInput(input: String): Either[String, String]

  def execute(input: String): Boolean

  def startGame(): Boolean

  def sendPlayersToGameService(playerList: List[String]): Boolean

  def load(): Boolean

  def loadFromDB(): Boolean

  def showErrorMessage(toString: String): Unit

}