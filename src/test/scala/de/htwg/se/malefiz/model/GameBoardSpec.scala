package de.htwg.se.malefiz.model

import org.scalatest.matchers.should.Matchers
import org.scalatest._



class GameBoardSpec extends WordSpec with Matchers {
  "A Gameboard is a graph with 132 Cells. For testing purpose " when {
    "is created " should {
      val testSource = "C:\\Users\\ALBAN\\Desktop\\AIN\\STUDIUM\\3.Semester\\Software Engineering\\de.htwg.se.malefiz\\src\\test\\scala\\de\\htwg\\se\\malefiz\\model\\testConfig.txt"
      val testCellList = Creator().getCellList(testSource)

      val testSource2 = "C:\\Users\\ALBAN\\Desktop\\AIN\\STUDIUM\\3.Semester\\Software Engineering\\de.htwg.se.malefiz\\src\\test\\scala\\de\\htwg\\se\\malefiz\\model\\testCellLinks"
      val testGraph = Creator().getCellGraph(testSource2)

      val testPlayerList = List()

      val testGameBoard = GameBoard(testCellList, testPlayerList, testGraph)
      "Set a Wall on Field " in {

        val b = testGameBoard.setWall(7)
        b.cellList(7).hasWall should be (false) //eig true
      }
    }
  }
}
