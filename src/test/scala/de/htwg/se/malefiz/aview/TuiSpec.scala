package de.htwg.se.malefiz.aview

import org.scalatest.matchers.should.Matchers
import org.scalatest._
import de.htwg.se.malefiz.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl.{Cell, Creator, GameBoard}
import de.htwg.se.malefiz.model.playerComponent.Player


import scala.collection.mutable.Map

class TuiSpec extends WordSpec with Matchers {

  "A Malefiz Tui" when {
    "when a new game start" should {
      val cellConfigFile = "project/mainCellConfiguration"
      val cellLinksFile = "project/mainCellLinks"

      val players: List[Player] = List().empty
      val cellList: List[Cell] = Creator().getCellList(cellConfigFile)
      val cellGraph: Map[Int, Set[Int]] = Creator().getCellGraph(cellLinksFile)
      val possibleCells: Set[Int] = Set().empty

      val controller = new Controller(GameBoard(cellList, players, cellGraph, possibleCells))
      val tui = new Tui(controller)

      "read names from the console" in {
        tui.processInput1("n Robert")
        tui.processInput1("n Alban")

        val playerList = controller.gameBoard.getPlayer

        playerList.head.playerNumber should be (1)
        playerList.head.name should be ("Robert")
        playerList(1).playerNumber should be (2)
        playerList(1).name should be ("Alban")
      }
    }
  }
}