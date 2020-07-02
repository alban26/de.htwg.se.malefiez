package de.htwg.se.malefiz

import com.google.inject.Guice

import scala.collection.mutable.Map
import de.htwg.se.malefiz.aview.Tui
import de.htwg.se.malefiz.aview.gui.{EntryGui, EntryPlayerGui, SwingGui}
import de.htwg.se.malefiz.controller.controllerComponent.ControllerInterface
import de.htwg.se.malefiz.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl.{Cell, Creator, GameBoard}
import de.htwg.se.malefiz.model.playerComponent.Player

import scala.io.StdIn.readLine

object Malefiz {

  val cellConfigFile = "project/mainCellConfiguration"
  val cellLinksFile = "project/mainCellLinks"

  /*
  val players : List[Player] = List().empty
  val cellList : List[Cell] = Creator().execute(Creator().getCellList,cellConfigFile)
  val cellGraph : Map[Int, Set[Int]] = Creator().execute1(Creator().getCellGraph, cellLinksFile)

  val controller = new Controller(GameBoard(cellList, players, cellGraph))
  val tui = new Tui(controller)
  val entryGui = new EntryGui(controller)
  val entryNamesGui = new EntryPlayerGui(controller)
  */

  val injector = Guice.createInjector(new MalefizModule)
  val controller = injector.getInstance(classOf[ControllerInterface])
  val tui = new Tui(controller)
  val entryGui = new EntryGui(controller)
  val swingGui = new SwingGui(controller)

  def main(args: Array[String]): Unit = {
    var input: String = ""

    if (!input.isEmpty) {
      input = args(0)
      tui.processInput1(input)
    }
    else do {
      input = readLine()
      tui.processInput1(input)
    } while (input != "end")
  }
}
