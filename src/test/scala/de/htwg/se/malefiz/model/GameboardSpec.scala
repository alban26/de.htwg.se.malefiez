package de.htwg.se.malefiz.model

import org.scalatest.WordSpec
import org.scalatest.{Matchers, WordSpec}

class GameboardSpec extends WordSpec with Matchers{
"The Gameboard exists of 112 field objects. These Objects need to be connected. The Graph" +
  "is realized as a Map of Sets. Map[Object,[Set[Objects]]" when {
  "is created" should {
    val genFields = GenFields()
    val listOfFields = genFields.genCells(112)
    val gameBoard = Gameboard()
    val graph = gameBoard.generateOriginal(listOfFields)
    "has 112 Map Entries" in {
      graph.size should be(112)
    }
    "Field 0 is connected to the Fields with fieldNumber 1 and 17" in {
      val t = graph.get(listOfFields(0))
      val s = t.head.toList
      s(0).fieldNumber should be(1)
      s(1).fieldNumber should be(17)
    }
  }
}
}
