package de.htwg.se.malefiz.controller.controllerComponent

import scala.swing.Publisher

trait ControllerInterface extends Publisher {

  def loadFromDB(): Unit

  def execute(input: String): Unit

  def sendPlayersToGameService(playerList: List[String]): Unit

  def startGame(): Unit

  def load(): Unit

  def checkInput(input: String): Either[String, String]

}