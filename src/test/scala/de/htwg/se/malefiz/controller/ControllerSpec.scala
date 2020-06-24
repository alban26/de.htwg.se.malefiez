package de.htwg.se.malefiz.controller


import de.htwg.se.malefiz.model.{Cell, Creator, GameBoard, Player}
import de.htwg.se.malefiz.util.Observer
import org.scalatest.matchers.should.Matchers
import org.scalatest._

import scala.collection.mutable.Map


class ControllerSpec  extends WordSpec with Matchers {
  "A Controller" when {

    "observed by an Observer" should {
      val cellConfigFile = "project/mainCellConfiguration"
      val cellLinksFile = "project/mainCellLinks"
      val players : List[Player] = List().empty
      val cellList : List[Cell] = Creator().getCellList(cellConfigFile)
      val cellGraph : Map[Int, Set[Int]] = Creator().getCellGraph(cellLinksFile)

      val controller = new Controller(GameBoard(cellList, players, cellGraph))
      //controller.player = List(Player(1, "Robert"), Player(1, "Alban"))
      controller.playersTurn = Player(1,"Robert")

      val observer = new Observer {
        var updated: Boolean = false
        def isUpdated: Boolean = updated
        override def update: Unit = {updated = true; updated}
      }



      "notify its Observer after a players figure is set on cell" in {
        controller.setPlayerFigure(1,1, 10)
        observer.updated should be(true)
        controller.getFigure(1,1) should be (10)
      }
      "notify its Observer after setting a Wall on a Cell" in {
        controller.setWall(50)
        observer.updated should be(true)
        controller.gameBoard.cellList(50).hasWall should be (true)
      }
      "notify its Observer after the cube is thrown which cells are possible to go" in {
        controller.getPCells(20, 5)
        observer.updated should be(true)
      }
      "Remove Player" in {
        controller.setPlayer(1,22)
        controller.setFigure(1,22)
        controller.gameBoard.cellList(22).playerNumber should be(1)
        controller.gameBoard.cellList(22).figureNumber should be(1)
      }
    }
  }
}

