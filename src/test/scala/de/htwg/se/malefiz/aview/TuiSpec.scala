package de.htwg.se.malefiz.aview

import de.htwg.se.malefiz.controller.Controller
import de.htwg.se.malefiz.model.{Cell, Creator, GameBoard, Player}
import org.scalatest.matchers.should.Matchers
import org.scalatest._

import scala.collection.mutable.Map

class TuiSpec extends WordSpec with Matchers {

  "A Sudoku Tui" should {
    val cellConfigFile = "project/mainCellConfiguration"
    val cellLinksFile = "project/mainCellLinks"

    val players : List[Player] = List().empty
    val cellList : List[Cell] = Creator().getCellList(cellConfigFile)
    val cellGraph : Map[Int, Set[Int]] = Creator().getCellGraph(cellLinksFile)


    val controller = new Controller(GameBoard(cellList, players, cellGraph))
    val tui = new Tui(controller)

    "do nothing on input 'q'" in {
      tui.processInput("q")
    }
    "create and empty Sudoku on input 'n'" in {
      tui.processInput("1 1 20")
      controller.gameBoard.cellList(20).playerNumber should be (1)
      controller.gameBoard.cellList(20).figureNumber should be (1)
    }
    "set a Wall on Cell '50'" in {
      tui.processInput("50")
      controller.gameBoard.cellList(50).hasWall should be (true)
    }

  }

}