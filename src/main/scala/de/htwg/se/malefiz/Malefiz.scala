package de.htwg.se.malefiz
import scala.collection.mutable.Map
import de.htwg.se.malefiz.aview.Tui
import de.htwg.se.malefiz.controller.Controller
import de.htwg.se.malefiz.model.{Cell, Creator, Cube, GameBoard, PlayFigure, Player}

import scala.io.StdIn.readLine

object Malefiz {

  val players : Array[Player] = Array()
  val cellList : List[Cell] = Creator().getCellList
  val cellGraph : Map[Int, Set[Int]] = Creator().getCellGraph


  val controller = new Controller(GameBoard(cellList, players, cellGraph))
  val tui = new Tui(controller)

  def main(args: Array[String]): Unit = {

    println("Willkommen bei Malefiz!")

    var input: String = ""

    do {
      input = readLine()
      tui.processInput(input)

    } while(input != "end")
  }
}
