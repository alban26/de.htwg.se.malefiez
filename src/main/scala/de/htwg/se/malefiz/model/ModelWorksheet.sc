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

val aMap = TreeMap[Int, TreeMap[Int, Int]](
  1 -> 2 -> 1), 4 -> TreeMap(3 -> 1)
)

println(aMap)
val t = List(1,2,3,4)

println(t(0))

val e = for {
  i <- 0 to 9
}yield e

println(e)
