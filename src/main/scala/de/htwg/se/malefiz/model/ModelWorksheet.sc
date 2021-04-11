import de.htwg.se.malefiz.aview.Tui
import de.htwg.se.malefiz.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl.{Cell, Creator}
import de.htwg.se.malefiz.playerModule.model.playerServiceComponent.Player

import scala.collection.mutable.Map

val cellConfigFile = "project/mainCellConfiguration"
val cellLinksFile = "project/mainCellLinks"

val players: List[Player] = List().empty
val cellList: List[Cell] = Creator().getCellList(cellConfigFile)
val cellGraph: Map[Int, Set[Int]] = Creator().getCellGraph(cellLinksFile)
val possibleCells: Set[Int] = Set().empty

val gameBoard = GameBoard(cellList, players, cellGraph, possibleCells)

val controller = new Controller(gameBoard)
val tui = new Tui(controller)

def getPossibleCells(start: Int, cube: Int): Set[Int] = {

  var found: Set[Int] = Set[Int]()
  var needed: Set[Int] = Set[Int]()

  def recurse(current: Int, times: Int): Unit = {

    if (times == 0 || cellList(current).hasWall && times == 0) {
      needed += current
    }
    if (times != 0 && cellList(current).hasWall) {
      return
    }
    found += current
    for (next <- cellGraph(current)) {
      if (!found.contains(next) && times != 0 ) {
        recurse(next, times-1)
      }
    }
  }
  recurse(start, cube)








}