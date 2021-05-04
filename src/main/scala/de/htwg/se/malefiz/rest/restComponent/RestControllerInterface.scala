package de.htwg.se.malefiz.rest.restComponent

trait RestControllerInterface {

  def sendLoadFromDBRequest(): Unit

  def startGameRequest(): Unit

  def sendPlayerListRequest(playerList: List[String]): Unit

  def sendLoadRequest(): Unit

  def openGameBoardRequest(): Unit

}
