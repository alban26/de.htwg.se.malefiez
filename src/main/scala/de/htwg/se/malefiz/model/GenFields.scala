package de.htwg.se.malefiz.model

case class GenFields() {
  val arrayWall1 = Array(22,26,30,34,38,60,64,71,74,83,102)
  val arrayWall2 = arrayWall1.map(x => x+1)
  val wallPermission = (0 to 17).toArray

  def genCells(n: Int): List[Field] = {
    if (n == 0) {
      Nil
    } else if (arrayWall2 contains n) {
      genCells(n - 1) :+ Field(n - 1, 0, false, true, Point(0, 0), true)
    }
    else if (wallPermission contains n) {
      genCells(n - 1) :+ Field(n - 1, 0, false, false, Point(0, 0), false)
    }
    else if (n == 112) {
      genCells(n - 1) :+ Field(n - 1, 0, true, false, Point(0, 0), false)
    }
    else {
      genCells(n - 1) :+ Field(n - 1, 0, false, true, Point(0, 0), false)
    }
  }

}
