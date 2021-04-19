package de.htwg.se.malefiz.rest.restComponent

trait RestControllerInterface {
  def sendLoadRequest(): Unit


  def startGameRequest(): Unit
  def sendPlayerListRequest(playerList: List[String]): Unit
  def openGameBoardRequest(): Unit

}
