package de.htwg.se.malefiz.model

import scala.collection.mutable.Map

import scala.io.Source

case class Creator() {

  val cellConfigFile = "/Users/robert/IdeaProjects/de.htwg.se.malefiz/src/main/scala/de/htwg/se/malefiz/model/mainCellConfiguration"
  val cellLinksFile = "/Users/robert/IdeaProjects/de.htwg.se.malefiz/src/main/scala/de/htwg/se/malefiz/model/mainCellLinks"

  def getCellList: List[Cell] = {
    val list = Source.fromFile(cellConfigFile)
    val inputData = list.getLines
      .map(line => line.split(" "))
      .map { case Array(cellNumber, destination, wallPermission, hasWall, x, y) =>
        Cell(cellNumber.toInt,
          destination.toBoolean,
          wallPermission.toBoolean,
          hasWall.toBoolean,
          Point(x.toInt, y.toInt))
      }
      .toList
    list.close()
    inputData
  }

  def getCellGraph: Map[Int, Set[Int]] = {
    val source = Source.fromFile(cellLinksFile)
    val lines = source.getLines()
    val graph : Map[Int, Set[Int]] = Map.empty
    while (lines.hasNext) {
      val input = lines.next()
      val inputArray: Array[String] = input.split(" ")
      for (i <- 1 until inputArray.length) {
        updateCellGraph(inputArray(0).toInt, inputArray(i).toInt, graph)
      }
    }
    graph
  }

  def updateCellGraph(key: Int, value: Int, map: Map[Int, Set[Int]]) : Map[Int, Set[Int]] = {
    map.get(key)
      .map(_=> map(key) += value)
      .getOrElse(map(key) = Set[Int](value))
    map
  }
}
