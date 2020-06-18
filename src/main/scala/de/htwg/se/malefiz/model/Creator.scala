package de.htwg.se.malefiz.model

import scala.collection.mutable.Map

import scala.io.Source

case class Creator() {


  def getCellList(inputFile: String): List[Cell] = {
    val list = Source.fromFile(inputFile)
    val inputData = list.getLines
      .map(line => line.split(" "))
      .map { case Array(cellNumber, playerNumber, figureNumber, destination, wallPermission, hasWall, x, y,possibilities) =>
        Cell(cellNumber.toInt,
          playerNumber.toInt,
          figureNumber.toInt,
          destination.toBoolean,
          wallPermission.toBoolean,
          hasWall.toBoolean,
          Point(x.toInt, y.toInt),
          possibilities.toBoolean)
      }
      .toList
    list.close()
    inputData
  }

  def getCellGraph(fileInput: String): Map[Int, Set[Int]] = {
    val source = Source.fromFile(fileInput)
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
