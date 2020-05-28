import de.htwg.se.malefiz.model.{CellOrdering, Field, Gameboard, GenFields}

import scala.collection.{SortedMap, mutable}
import scala.collection.immutable.TreeMap


case class Cell(feldnummer: Int) {
  override def toString:String = "[]"
}

def generateOriginal(list: List[Cell]): Unit = {
  var n = 116

  for (i <- 0 to 13) {
    for (j <- 0 to 16) {
      var t: SortedMap[Cell, Set[Cell]] =  SortedMap(list(n) -> Set(list(n+1))) (CellOrdering)
      print(list(n).toString)

    }
    n = n - 16
    println()
  }

}

object CellOrdering extends Ordering[Cell] {
  override def compare(x: Cell, y: Cell): Int = x.feldnummer - y.feldnummer
}