import de.htwg.se.malefiz.model.{Gameboard, GenFields}

import scala.collection.mutable
import scala.collection.immutable.TreeMap

case class Cell(x:Int, y:Int)

val cell1 = Cell(4,5)
cell1.x
cell1.y

case class Field(cells: Array[Cell])

val field1 = Field(Array.ofDim[Cell](1))
field1.cells(0)=cell1
field1.cells(0).x
field1.cells(0).y




val genFields = GenFields()
val listOfFields = genFields.genCells(112)
val gameBoard = Gameboard()
val graph = gameBoard.generateOriginal(listOfFields)

val t = graph.get(listOfFields(0))

val s = t.head.toList

s(0).fieldNumber == 1
s(1).fieldNumber == 17

case class Cube() {


  def getRandomNumber : Int = {
    val randomNumber = scala.util.Random.nextInt(6) + 1
    randomNumber
  }

}

val test = Cube()
val testNumber = test.getRandomNumber

