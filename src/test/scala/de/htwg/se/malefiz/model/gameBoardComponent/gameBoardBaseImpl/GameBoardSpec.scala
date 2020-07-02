package de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl

import de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl
import org.scalatest._
import org.scalatest.matchers.should.Matchers

class GameBoardSpec extends WordSpec with Matchers {
  "A Gameboard is a graph with 132 Cells. For testing purpose " when {
    "is created " should {
      val testSourceConfig = "project/testConfig.txt"
      val testSourceLinks = "project/testCellLinks.txt"

      val testCellList = Creator().getCellList(testSourceConfig)
      val testGraph = Creator().getCellGraph(testSourceLinks)

      val mainSourceConfig = "project/mainCellConfiguration"
      val mainSourceLinks =  "project/mainCellLinks"
      val testPlayerList = List()
      val testGameBoard = GameBoard(testCellList, testPlayerList, testGraph, Set().empty)

      val mainCellList = Creator().getCellList(mainSourceConfig)
      val mainCellGraph = Creator().getCellGraph(mainSourceLinks)

      val main = gameBoardBaseImpl.GameBoard(mainCellList, testPlayerList, mainCellGraph, Set().empty)

      "Length of the testList" in {
        testGameBoard.cellList.length should be (10)
      }
      "Length of the mainList" in {
        main.cellList.length should be (132)
      }
      "Check if there is a Wall on Cell 52" in {
        main.cellList(52).hasWall should be (false)
      }
      "Set Wall on Cell 52" in {
        var c = main.setWall(52)
        c.cellList(52).hasWall should be (true)
      }
      "remove Wall on Cell 52" in {
        var d = main.rWall(52)
        d.cellList(52).hasWall should be (false)
      }
      "hasWall in Cell 55 should be set from false to true "in {
        val x =  main.placeWall(55)
        x.hasWall should be (true)
      }
      "hasWall in Cell 55 should be set from true to false" in {
        val y = main.removeWall(55)
        y.hasWall should be (false)
      }
      "hasWall in Cell 77 should be set from false to true" in {
        val y = main.updateListWall(77)
        y(77).hasWall should be (true)
      }
      "if we throw the cube and get a 5 and take the 1 first figure from player 1" in {
        val x =  main.getPossibleCells(0, 5)
        x.possibleCells.contains(42) should be (true)
        x.possibleCells.contains(26) should be (true)
        x.possibleCells.contains(46) should be (true)
      }
      " location of figure 2 from player 2 " in {
        val x = main.getPlayerFigure(2, 2)
        x should be (6)
      }
      " set player 2 on cell 30 " in {
        val x = main.setPlayer(2, 30)
        x.cellList(30).playerNumber should be (2)
      }
      " set player 2 figure 2 on cell 30 " in {
        val x = main.setFigure(2, 30)
        x.cellList(30).figureNumber should be (2)
      }
      " remove play2 and figure 2 from cell 30" in {
        val x = main.removeActualPlayerAndFigureFromCell(2, 2)
        x.cellList(30).figureNumber should be (0)
        x.cellList(30).figureNumber should be (0)
      }
      " get the Homenumber of a Players Figure" in {
        main.getHomeNr(1 ,1) should be (0)
      }
      "build the whole gameboard as a String" in {

        val testCellList = Creator().getCellList(mainSourceConfig)
        val testGameBoard = gameBoardBaseImpl.GameBoard(testCellList, testPlayerList, testGraph, Set().empty)

        val gameBoardString =
          """|                                [ ]
             |[ ]-[ ]-[ ]-[ ]-[ ]-[ ]-[ ]-[ ]-[X]-[ ]-[ ]-[ ]-[ ]-[ ]-[ ]-[ ]-[ ]
             |[ ]                                                             [ ]
             |[ ]-[ ]-[ ]-[ ]-[ ]-[ ]-[ ]-[ ]-[X]-[ ]-[ ]-[ ]-[ ]-[ ]-[ ]-[ ]-[ ]
             |                                [X]
             |                        [ ]-[ ]-[X]-[ ]-[ ]
             |                        [ ]             [ ]
             |                [ ]-[ ]-[X]-[ ]-[ ]-[ ]-[X]-[ ]-[ ]
             |                [ ]                             [ ]
             |        [ ]-[ ]-[ ]-[ ]-[ ]-[ ]-[ ]-[ ]-[ ]-[ ]-[ ]-[ ]-[ ]
             |        [ ]             [ ]             [ ]             [ ]
             |[X]-[ ]-[ ]-[ ]-[X]-[ ]-[ ]-[ ]-[X]-[ ]-[ ]-[ ]-[X]-[ ]-[ ]-[ ]-[X]
             |[ ]             [ ]             [ ]             [ ]             [ ]
             |[ ]-[ ]-[ ]-[ ]-[ ]-[ ]-[ ]-[ ]-[ ]-[ ]-[ ]-[ ]-[ ]-[ ]-[ ]-[ ]-[ ]
             |    (1)(1)(1)(1)(1)(2)(2)(2)(2)(2)(3)(3)(3)(3)(3)(4)(4)(4)(4)(4)
             |""".stripMargin

        val playerString =
          """    (1)(1)(1)(1)(1)(2)(2)(2)(2)(2)(3)(3)(3)(3)(3)(4)(4)(4)(4)(4)
            |""".stripMargin

        testGameBoard.createGameBoard() should be(gameBoardString)
        testGameBoard.buildString(testGameBoard.cellList) should be (gameBoardString)
        testGameBoard.buildPlayerString(testGameBoard.cellList) should be (playerString)
      }
    }
  }
}
