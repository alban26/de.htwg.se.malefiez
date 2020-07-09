package de.htwg.se.malefiz.aview
/*
import com.google.inject.{Guice, Injector}
import org.scalatest.matchers.should.Matchers
import org.scalatest._
import de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl.{Cell, Creator, GameBoard}
import com.google.inject.Guice
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.malefiz.MalefizModule
import de.htwg.se.malefiz.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.malefiz.controller.controllerComponent.{ControllerInterface, GameBoardChanged, State}
import de.htwg.se.malefiz.model.gameBoardComponent.GameboardInterface
import de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl.Cell
import de.htwg.se.malefiz.model.playerComponent.Player

import scala.collection.mutable.Map

class TuiSpec extends WordSpec with Matchers {



  "A Malefiz Tui" when {
    "when a new game start" should {
      val cellConfigFile = "project/mainCellConfiguration"
      val cellLinksFile = "project/mainCellLinks"

      val players: List[Player] = List().empty
      val cellList: List[Cell] = Creator().getCellList(cellConfigFile)
      val cellGraph: Map[Int, Set[Int]] = Creator().getCellGraph(cellLinksFile)
      val possibleCells: Set[Int] = Set().empty

      var gameBoard = GameBoard(cellList, players, cellGraph, possibleCells)

      val controller = new Controller(gameBoard)
      val tui = new Tui(controller)
      "At the beginning you will entry to the Setup-State" +
        "In our case its defined as Number -> 4" in {
        controller.s.state.toString should be ("4")
      }
      "read names from the console" in {
        tui.processInput1("n Robert")
        tui.processInput1("n Alban")

        val playerList = controller.getPlayer

        playerList.head.playerNumber should be(1)
        playerList.head.name should be("Robert")
        playerList(1).playerNumber should be(2)
        playerList(1).name should be("Alban")
      }
      "When the game gets started by a full Players List or if typed in start, " +
        "in this case it's Roberts turn. The state's gonna be 'Roll' in our case it's -> 1 " in {
        tui.processInput1("n start")
        controller.playersTurn.playerNumber should be (1)
        controller.playersTurn.name should be ("Robert")
        controller.s.state.toString should be ("1")
      }
      "So when Robert is pressing any key now, he's going to roll the dice" in {
        tui.processInput1("k")
        controller.dicedNumber should (be >= 1 and be < 7)
      }
      "Now Robert needs to select his Figure. In this case he gets to the next State " +
        "The 'Select Figure State' is the Number 2" in {
        controller.s.state.toString should be ("2")
      }
      "Well so let's say he chooses his second Figure. In this case " +
        "he types in '1 2', because he is the first player (1) and wants his second figure(2)" +
        "So when he choose, the variable selectedFigures got filled with these numbers" +
        "Also he gets to the next state SetFigure!" in {
        tui.processInput1("1 2")
        controller.selectedFigure should be(1,2)
      }
    }
  }
}
*/

