package de.htwg.se.malefiz.aview
/*
import de.htwg.se.malefiz.controller.{Controller, GameState}
import de.htwg.se.malefiz.model.{Cell, Creator, GameBoard, Player}
import org.scalatest.matchers.should.Matchers
import org.scalatest._
import GameState._
import de.htwg.se.malefiz.controller.PlayingState._

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
    "call input with \"Robert Alban\" the state is firstly ENTRY_NAMES" in {
      tui.input("Robert Alban")
      controller.gameState should be (PLAYERS_TURN)
      controller.player.mkString should be ("1 -->  Robert2 -->  Alban")
      controller.playersTurn should be (controller.player(0))
      tui.update should be (true)
    }
    "next up in the processInput the game continues " in {

      controller.playingState should be(ROLL)
      tui.processInput("b")
      tui.update should be (true)
      controller.gameBoard.cellList(0).possibleFigures should be(true)
      controller.playingState should be(SELECT_PLAYER)
      tui.processInput("1 1")
      tui.update should be (true)
      controller.playingState should be(SET_PLAYER)
      controller.getPCells(controller.getFigure(1,1),1)
      controller.gameBoard.possibleCells should be (Set(22))
      controller.gameBoard.cellList(0).possibleFigures should be(false)
      tui.update should be (true)

    }

    "set figure 1 from player 1 to cell 20" in {
      tui.processInput1("1 1 20")
      controller.gameBoard.cellList(20).playerNumber should be (1)
      controller.gameBoard.cellList(20).figureNumber should be (1)
     // controller.gameBoard.cellList(0).playerNumber should be (0)
     // controller.gameBoard.cellList(0).figureNumber should be (0)
      tui.processInput1("z")
      controller.gameBoard.cellList(20).playerNumber should be (0)
      controller.gameBoard.cellList(20).figureNumber should be (0)
      tui.processInput1("y")
      controller.gameBoard.cellList(20).playerNumber should be (1)
      controller.gameBoard.cellList(20).figureNumber should be (1)
    }
    "set a Wall on Cell '53'" in {
      tui.processInput1("53")
      controller.gameBoard.cellList(53).hasWall should be (true)
      tui.processInput1("z")
      controller.gameBoard.cellList(53).hasWall should be (false)
      tui.processInput1("y")
      controller.gameBoard.cellList(50).hasWall should be (true)
    }
    "get possible cells from startcell 0 with a random number 5" in {
      tui.processInput1("0 5")
      controller.gameBoard.possibleCells.contains(42) should be (true)
      controller.gameBoard.possibleCells.contains(46) should be (true)
      controller.gameBoard.possibleCells.contains(26) should be (true)
    }
  }

}*/