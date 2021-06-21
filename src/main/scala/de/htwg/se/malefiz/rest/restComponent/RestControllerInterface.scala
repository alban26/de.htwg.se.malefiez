package de.htwg.se.malefiz.rest.restComponent

trait RestControllerInterface {

  def sendLoadFromDBRequest(): Unit

  def startGameRequest(): Boolean

  def sendPlayerListRequest(playerList: List[String]): Unit

  def sendLoadRequest(): Boolean

  def openGameBoardRequest(): Unit

}
