package de.htwg.se.malefiz

import scala.collection.mutable.Map
import de.htwg.se.malefiz.aview.Tui
import de.htwg.se.malefiz.controller.Controller
import de.htwg.se.malefiz.model.{Cell, Creator, Cube, GameBoard, Player}


import scala.io.StdIn.readLine

object Malefiz {

  val cellConfigFile = "project/mainCellConfiguration"
  val cellLinksFile = "project/mainCellLinks"

  val players : List[Player] = List().empty

  val cellList : List[Cell] = Creator().execute(Creator().getCellList,cellConfigFile)
  val cellGraph : Map[Int, Set[Int]] = Creator().execute1(Creator().getCellGraph, cellLinksFile)


  val controller = new Controller(GameBoard(cellList, players, cellGraph))
  val tui = new Tui(controller)

  def main(args: Array[String]): Unit = {

    var input: String = ""

    if (!input.isEmpty) {
      input = args(0)
      tui.processInput(input)
    }
    else do {
      input = readLine()
      tui.input(input)
    } while (input != "end")
  }
}
