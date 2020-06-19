package de.htwg.se.malefiz
import com.sun.corba.se.impl.orbutil.graph.Graph

import scala.collection.mutable.Map
import de.htwg.se.malefiz.aview.Tui
import de.htwg.se.malefiz.controller.Controller
import de.htwg.se.malefiz.model.{Cell, Creator, Cube, GameBoard, Player}
import de.htwg.se.malefiz.model.Creator._

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

    println("Welcome to Malefiz: ")
    var input: String = ""

    if (!input.isEmpty) {
      tui.processInput(input)
    }
    else do {
      input = readLine()
      tui.input(input)
    } while (input != "end")
  }
}
