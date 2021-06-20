package de.htwg.se.malefiz.gameBoardModule.model.gameBoardComponent.gameBoardBaseImpl

import de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent.Statements.addPlayer
import org.scalatest._
import org.scalatest.matchers.should.Matchers

class GameBoardSpec extends WordSpec with Matchers {

  "The Gameboard datastructure is a graph with 132 Cells." when {
    "the Gameboard is created" should {

      val cellConfigFile = "/configuration/mainCellConfiguration"
      val cellLinksFile = "/configuration/mainCellLinks"
      val players: List[Option[Player]] = List(Some(Player(1, "Robert")))
      val cellList: List[Cell] = Creator().getCellList(cellConfigFile)
      val cellGraph = Creator().getCellGraph(cellLinksFile)

      val testGameBoard =
        GameBoard(cellList, players, cellGraph, Set().empty, Option.empty, None, None, None, Option(addPlayer))

      "Length of the mainList" in {
        testGameBoard.cellList.length should be(132)
      }
      "Home Number of the first figure of player one" in {
        testGameBoard.getHomeNr(1, 1) should be (0)
      }
    }
  }
}
