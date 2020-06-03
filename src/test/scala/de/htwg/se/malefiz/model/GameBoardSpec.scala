package de.htwg.se.malefiz.model

import org.scalatest._
import org.scalatest.matchers.should.Matchers


class GameBoardSpec extends WordSpec with Matchers {
  "A Gameboard is a graph with 116 Cells. For testing purpose we crated a new config testfile. The new testfile" when {
    "is created " should {
      val testSource = "/Users/robert/IdeaProjects/htwg-scala-seed/src/test/scala/de/htwg/se/Malefiz/model/testConfig.txt"
      val testCellList = GameBoard().getCellList(testSource)
      val listLength = testCellList.length
      "have 10 Cells with different configurated attributes " in {
        listLength should be (10)
      }
      "the first four cells should be matched to a playernumber " in {
        testCellList.head.playerNumber should be (1)
        testCellList(1).playerNumber should be (2)
        testCellList(2).playerNumber should be (3)
        testCellList(3).playerNumber should be (4)
      }
      "all Cells should have the permission to be the destination. Only the last cell ist the destination" in {
        testCellList.head.destination should be (false)
        testCellList(1).destination should be (false)
        testCellList(2).destination should be (false)
        testCellList(3).destination should be (false)
        testCellList(4).destination should be (false)
        testCellList(5).destination should be (false)
        testCellList(6).destination should be (false)
        testCellList(7).destination should be (false)
        testCellList(8).destination should be (false)
        testCellList(9).destination should be (true)

      }
      "the first six Cells shouldnt have the permission to set a Wall" in {
        testCellList.head.wallPermission should be (false)
        testCellList(1).wallPermission should be (false)
        testCellList(2).wallPermission should be (false)
        testCellList(3).wallPermission should be (false)
        testCellList(4).wallPermission should be (false)
        testCellList(5).wallPermission should be (false)
      }
      "the Cell 6,7,8 should have the permission to set a Wall." in {
        testCellList(6).wallPermission should be (true)
        testCellList(7).wallPermission should be (true)
        testCellList(8).wallPermission should be (true)
      }
      "all Cells instead of the Number 8 shouldnt have a Wall" in {
        testCellList.head.hasWall should be (false)
        testCellList(1).hasWall should be (false)
        testCellList(2).hasWall should be (false)
        testCellList(3).hasWall should be (false)
        testCellList(4).hasWall should be (false)
        testCellList(5).hasWall should be (false)
        testCellList(6).hasWall should be (false)
        testCellList(7).hasWall should be (false)
      }
      "the Cell with the Cell number 8 should have a Wall" in {
        testCellList(8).hasWall should be (true)
      }
      "the last cell shouldnt have the permission to set a wall and also be the destination" in {
        testCellList(9).wallPermission should be (false)
        testCellList(9).destination should be (true)
      }
    }
  }
  "To Generate the Gameborad with a graph we read the cells with links from a txt-file. The graph" when {
    "is created" should {
      val fileInput = "/Users/robert/IdeaProjects/htwg-scala-seed/src/test/scala/de/htwg/se/Malefiz/model/testCellLinks"
      val testGraph = GameBoard().getCellGraph(fileInput)
      "have a size of 10 " in {
        testGraph.size should be (10)
      }
      "to add a Cell link we call the function updateCellGraph" in {
        val updateTest = GameBoard().updateCellGraph(7, 9, testGraph)
        testGraph.get(7) should be (Some(Set(6,8,9)))
      }
    }
  }

}
