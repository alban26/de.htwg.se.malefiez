package de.htwg.se.malefiz.model

import scala.io.Source

case class ListCreator() {

  val cellConfigFile = "/Users/robert/IdeaProjects/de.htwg.se.malefiz/src/main/scala/de/htwg/se/malefiz/model/mainCellConfiguration"
  val cellLinksFile = "/Users/robert/IdeaProjects/de.htwg.se.malefiz/src/main/scala/de/htwg/se/malefiz/model/mainCellLinks"

  def getCellList: List[Cell] = {
    val list = Source.fromFile(cellConfigFile)
    val inputData =  list.getLines
      .map(line => line.split(" "))
      .map{case Array(cellNumber,destination, wallPermission, hasWall, x,y) =>
        Cell(cellNumber.toInt,
          destination.toBoolean,
          wallPermission.toBoolean,
          hasWall.toBoolean,
          Point(x.toInt, y.toInt))}
      .toList
    list.close()
    inputData
  }

}
