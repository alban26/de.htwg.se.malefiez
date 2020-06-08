package de.htwg.se.malefiz.model

import scala.io.Source

case class ListCreator() {

  val cellConfigFile = "C:\\Users\\ALBAN\\Desktop\\AIN\\STUDIUM\\3.Semester\\Software Engineering\\de.htwg.se.malefiz\\src\\main\\scala\\de\\htwg\\se\\malefiz\\model\\mainCellConfiguration"
  val cellLinksFile = "C:\\Users\\ALBAN\\Desktop\\AIN\\STUDIUM\\3.Semester\\Software Engineering\\de.htwg.se.malefiz\\src\\main\\scala\\de\\htwg\\se\\malefiz\\model\\mainCellLinks"

  def getCellList: List[Cell] = {
    val list = Source.fromFile(cellConfigFile)
    val inputData =  list.getLines
      .map(line => line.split(" "))
      .map{case Array(cellNumber, playerNumber, destination, wallPermission, hasWall, x,y) =>
        Cell(cellNumber.toInt,
          null,
          destination.toBoolean,
          wallPermission.toBoolean,
          hasWall.toBoolean,
          Point(x.toInt, y.toInt))}
      .toList
    list.close()
    inputData
  }

}
