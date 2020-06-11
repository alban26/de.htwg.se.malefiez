package de.htwg.se.malefiz.model

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
      val testGameBoard = GameBoard(testCellList, testPlayerList, testGraph)

      val mainCellList = Creator().getCellList(mainSourceConfig)
      val mainCellGraph = Creator().getCellGraph(mainSourceLinks)

      val b = GameBoard(mainCellList, testPlayerList, mainCellGraph)

      "Length of the testList" in {
        testGameBoard.cellList.length should be (10)
      }
      "Length of the mainList" in {
        b.cellList.length should be (132)
      }
      "Check if there is a Wall on Cell 52" in {
        b.cellList(52).hasWall should be (false)
      }
      "Set Wall on Cell 52" in {
        var c = b.setWall(52)
        c.cellList(52).hasWall should be (true)
      }
      "build the whole gameboard as a String" in {
        val testSource = "src/main/scala/de/htwg/se/malefiz/model/mainCellConfiguration"
        val testCellList = Creator().getCellList(mainSourceConfig)
        val testGameBoard = GameBoard(testCellList, testPlayerList, testGraph)

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
             |[ ]-[ ]-[ ]-[ ]-[ ]-[ ]-[X]-[ ]-[ ]-[ ]-[X]-[ ]-[ ]-[ ]-[ ]-[ ]-[ ]
             |    () () () () () () () () () () () () () () () () () () () ()
             |""".stripMargin

        val playerString =
          """    () () () () () () () () () () () () () () () () () () () ()
            |""".stripMargin

        testGameBoard.createGameBoard() should be(gameBoardString)
        testGameBoard.buildString(testGameBoard.cellList) should be (gameBoardString)
        testGameBoard.buildPlayerString(testGameBoard.cellList) should be (playerString)
      }




    }
  }
}
