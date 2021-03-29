package de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl

import de.htwg.se.malefiz.model.gameBoardComponent.{CreatorInterface, gameBoardBaseImpl}
import scala.collection.mutable.Map
import scala.io.Source
import scala.util.{Failure, Success, Try}

case class Creator() extends CreatorInterface {

  def readTextFile(filename: String): Try[Iterator[String]] =
    Try(Source.fromFile(filename).getLines)

  def getCellList(inputFile: String): List[Cell] =
    readTextFile(inputFile) match {
      case Success(line) =>
        println("Welcome")
        val list = Source.fromFile(inputFile)
        val inputData = list.getLines
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
          }
          .toList
        list.close()
        inputData
      case Failure(f) =>
        println(f)
        List.empty
    }

  def getCellGraph(fileInput: String): Map[Int, Set[Int]] =
    readTextFile(fileInput) match {
      case Success(line) =>
        println("to Malefiz!")

        val source = Source.fromFile(fileInput)
        val lines = source.getLines()
        val graph: Map[Int, Set[Int]] = Map.empty
        while (lines.hasNext) {
          val input = lines.next()
          val inputArray: Array[String] = input.split(" ")
          inputArray.map(input => updateCellGraph(inputArray(0).toInt, input.toInt, graph))
          //for (i <- 1 until inputArray.length)
          //  updateCellGraph(inputArray(0).toInt, inputArray(i).toInt, graph)
        }
        graph
      case Failure(f) =>
        println(f)
        Map.empty
    }

  def updateCellGraph(key: Int, value: Int, map: Map[Int, Set[Int]]): Map[Int, Set[Int]] = {
    map
      .get(key)
      .map(_ => map(key) += value)
      .getOrElse(map(key) = Set[Int](value))
    map
  }

}
