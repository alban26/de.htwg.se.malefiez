package de.htwg.se.malefiz.aview

import de.htwg.se.malefiz.controller.Controller
import de.htwg.se.malefiz.model.{Cell, Creator, GameBoard, Player}
import org.scalatest.matchers.should.Matchers
import org.scalatest._

import scala.collection.mutable.Map

class TuiSpec extends WordSpec with Matchers {

  "A Malefiz Tui" should {
    val cellConfigFile = "project/mainCellConfiguration"
    val cellLinksFile = "project/mainCellLinks"

    val players : List[Player] = List().empty
    val cellList : List[Cell] = Creator().getCellList(cellConfigFile)
    val cellGraph : Map[Int, Set[Int]] = Creator().getCellGraph(cellLinksFile)


    val controller = new Controller(GameBoard(cellList, players, cellGraph))
    controller.player = List(Player(1, "Robert"), Player(1, "Alban"))
    controller.playersTurn = Player(1, "Robert")
    val tui = new Tui(controller)
    /*
    "do nothing on input 's'" in {
      tui.processInput("s")
    }
    */

    "set figure 1 from player 1 to cell 20" in {
      tui.processInput1("1 1 20")
      controller.gameBoard.cellList(20).playerNumber should be (1)
      controller.gameBoard.cellList(20).figureNumber should be (1)
    }
    "set a Wall on Cell '50'" in {
      tui.processInput1("50")
      controller.gameBoard.cellList(50).hasWall should be (true)
    }
    "get possible cells from startcell 0 with a random number 5" in {
      tui.processInput1("0 5")
      controller.gameBoard.possibleCells.contains(42) should be (true)
      controller.gameBoard.possibleCells.contains(46) should be (true)
      controller.gameBoard.possibleCells.contains(26) should be (true)
    }
  }

}