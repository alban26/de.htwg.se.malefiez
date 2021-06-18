package de.htwg.se.malefiz.gameBoardModule.model.gameBoardComponent.gameBoardBaseImpl

import java.io.InputStream

import de.htwg.se.malefiz.gameBoardModule.model.gameBoardComponent.{CreatorInterface, gameBoardBaseImpl}

import scala.collection.mutable.Map

case class Creator() extends CreatorInterface {


  def getCellList(inputFile: String): List[Cell] = {
    val stream: InputStream = getClass.getResourceAsStream(inputFile)
    scala.io.Source.fromInputStream(stream).getLines
      .map(line => line.split(" "))
      .map {
        case Array(
        cellNumber,
        playerNumber,
        figureNumber,
        wallPermission,
        hasWall,
        x,
        y,
        possibleFigures,
        possibleCells
        ) =>
          gameBoardBaseImpl.Cell(
            cellNumber.toInt,
            playerNumber.toInt,
            figureNumber.toInt,
            wallPermission.toBoolean,
            hasWall.toBoolean,
            Point(x.toInt, y.toInt),
            possibleFigures.toBoolean,
            possibleCells.toBoolean
          )
      }.toList
  }

  def getCellGraph(fileInput: String): Map[Int, Set[Int]] = {

    val stream: InputStream = getClass.getResourceAsStream(fileInput)
    val lines = scala.io.Source.fromInputStream(stream).getLines
    val graph: Map[Int, Set[Int]] = Map.empty
    while (lines.hasNext) {
      val input = lines.next()
      val inputArray: Array[String] = input.split(" ")
      val keyValue = inputArray(0)
      inputArray.update(0, "")
      inputArray.map(input =>
        if (input != "") {
          updateCellGraph(keyValue.toInt, input.toInt, graph)
        })
    }
    graph
  }

  def updateCellGraph(key: Int, value: Int, map: Map[Int, Set[Int]]): Map[Int, Set[Int]] = {
    map
      .get(key)
      .map(_ => map(key) += value)
      .getOrElse(map(key) = Set[Int](value))
    map
  }

}
