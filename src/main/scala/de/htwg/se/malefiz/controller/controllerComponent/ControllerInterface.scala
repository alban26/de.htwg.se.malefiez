package de.htwg.se.malefiz.controller.controllerComponent

import scala.swing.Publisher

trait ControllerInterface extends Publisher {

  def checkInput(input: String): Either[String, String]

  def execute(input: String): Unit

  def startGame(): Unit

  def sendPlayersToGameService(playerList: List[String]): Unit

  def load(): Unit

  def loadFromDB(): Unit

  def showErrorMessage(toString: String): Unit

}