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
  }
}
}
