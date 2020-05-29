package de.htwg.se.malefiz.model

case class GenFields() {
  def numberOfNodes(n: Int): Int = {
    if (n == 0) 1
    else
      singleNodesBetween(n, 3) + nodesBetween(n) + bottomTopNodes(n)
  }

  def singleNodesBetween(n: Int, fix: Int): Int = {
    if (n == 1) {
      6
    } else {
      fix + singleNodesBetween(n - 1, fix + 1)
    }
  }


  def nodesBetween(n: Int): Int = {
    nodesBetweenHilf(n * 4 + 1)
  }

  def nodesBetweenHilf(n: Int): Int = {
    if (n == 1) {
      0
    } else {
      n + nodesBetweenHilf(n - 4)
    }
  }

  def s(n: Int): Int = n * 4 + 1

  def bottomTopNodes(n: Int): Int = {
    s(n) * 3
  }


  def steineUnten(n: Int): List[Int] = {
    val s = if (n > 1) n - 1 else 0
    steineUntenHilf(7 + (s * 5), n)
  }

  def steineUntenHilf(n: Int, m: Int): List[Int] = {
    if (m == -1) {
      Nil
    } else {
      n :: steineUntenHilf(n + 4, m - 1)
    }
  }


  def dreiSteine(n: Int): List[Int] = {
    val m = nodesBetween(n) + singleNodesBetween(n, 3) + s(n) - 7
    dreiSteineHilf(m, 3, s(n))
  }

  def dreiSteineHilf(n: Int, m: Int, t: Int): List[Int] = {
    if (m == 0) {
      Nil
    } else if (m == 2) {
      val tn = math.ceil(t.toDouble / 2).toInt
      n :: dreiSteineHilf(n + tn, m - 1, t)
    }
    else {
      n :: dreiSteineHilf(n + 3, m - 1, t)
    }
  }

  def twoWalls(l: List[Int]): List[Int] = {
    val s: List[Int] = List(l(0) - 11, l(0) - 7)
    s
  }

  def wallBeforeAim(n: Int): List[Int] = List(numberOfNodes(n) - math.ceil(s(n).toDouble / 2).toInt - 1)


  def buildWalls(n: Int): List[Int] = {
    val s = if (n > 2) twoWalls(dreiSteine(n)) else Nil
    steineUnten(n) ::: dreiSteine(n) ::: s ::: wallBeforeAim(n)
  }


  def genCells(n: Int): List[Field] = {

    val steine2 = buildWalls(n)
    val steine = steine2.map(x => x + 1)
    val wallPermission = (0 to s(n)).toList
    val destination = numberOfNodes(n) - 1
    val anzahl = numberOfNodes(n)

    genCellsFinal(steine, wallPermission, destination, anzahl)
  }

  def genCellsFinal(steine: List[Int], wallP: List[Int], desti: Int, n: Int): List[Field] = {
    if (n == 0) {
      Nil
    } else if (steine contains n) {
      genCellsFinal(steine, wallP, desti, n - 1) :+ Field(n - 1, 0, false, true, Point(0, 0), true)
    }
    else if (wallP contains n) {
      genCellsFinal(steine, wallP, desti, n - 1) :+ Field(n - 1, 0, false, false, Point(0, 0), false)
    }
    else if (n == desti) {
      genCellsFinal(steine, wallP, desti, n - 1) :+ Field(n - 1, 0, true, false, Point(0, 0), false)
    }
    else {
      genCellsFinal(steine, wallP, desti, n - 1) :+ Field(n - 1, 0, false, true, Point(0, 0), false)
    }
  }



}
