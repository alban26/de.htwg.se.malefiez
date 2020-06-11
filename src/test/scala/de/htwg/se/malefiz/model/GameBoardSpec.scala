package de.htwg.se.malefiz.model

import org.scalatest._
import org.scalatest.matchers.should.Matchers



class GameBoardSpec extends WordSpec with Matchers {
  "A Gameboard is a graph with 132 Cells. For testing purpose " when {
    "is created " should {
      val testSource = "project/testConfig.txt"
      val testSource3 = "project/mainCellConfiguration"
      val testCellList = Creator().getCellList(testSource)

      val testSource2 = "project/testCellLinks.txt"
      val testGraph = Creator().getCellGraph(testSource2)

      val testPlayerList = List()

      val testGameBoard = GameBoard(testCellList, testPlayerList, testGraph)
      "Set a Wall on Field " in {

        val b = testGameBoard.setWall(7)
        b.cellList(7).hasWall should be (false)
      }
      "build the whole gameboard as a String" in {
        val testSource = "src/main/scala/de/htwg/se/malefiz/model/mainCellConfiguration"
        val testCellList = Creator().getCellList(testSource3)
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
