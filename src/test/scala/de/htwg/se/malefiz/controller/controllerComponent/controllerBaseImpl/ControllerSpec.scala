package de.htwg.se.malefiz.controller


import de.htwg.se.malefiz.controller.controllerComponent.GameStates.{GameState, Roll}
import de.htwg.se.malefiz.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl.{Cell, Creator, GameBoard}
import de.htwg.se.malefiz.model.playerComponent.Player
import de.htwg.se.malefiz.util.Observer
import org.scalatest.matchers.should.Matchers
import org.scalatest._

import scala.collection.mutable.Map


class ControllerSpec  extends WordSpec with Matchers {
  "A Controller" when {

    "observed by an Observer" should {
      val cellConfigFile = "project/mainCellConfiguration"
      val cellLinksFile = "project/mainCellLinks"
      val players : List[Player] = List().empty
      val cellList : List[Cell] = Creator().getCellList(cellConfigFile)
      val cellGraph : Map[Int, Set[Int]] = Creator().getCellGraph(cellLinksFile)

      val controller = new Controller(GameBoard(cellList, players, cellGraph, Set().empty))

      controller.playersTurn = Player(1,"Robert")

      val observer: Observer = new Observer {
        var updated: Boolean = false
        def isUpdated: Boolean = updated
        override def update(): Unit = {updated = true}
      }
      "Set Players" in {
        controller.createPlayer("Robert")
        controller.createPlayer("Alban")

        controller.getPlayer.head.name should be ("Robert")
        controller.getPlayer(1).name should be ("Alban")
      }
      "notify its Observer after a players figure is set on cell" in {
        controller.setPlayerFigure(1,1, 10)
        observer.update should be()
        controller.getFigure(1,1) should be (10)
      }
      "notify its Observer after setting a Wall on a Cell" in {
        controller.setWall(50)
        controller.gameBoard.getCellList(50).hasWall should be(true)
        observer.update should be()
      }
      "The Controller has the abililty to remove a wall" in {
        controller.rWall(50)
        controller.gameBoard.getCellList(50).hasWall should be(false)
      }
      "The Controller has the ability to save the current selected figure of a player" in {
        controller.setSelectedFigures(1,5)
        controller.getSelectedFigure should be ((1,5))
      }
      "The Controller has the ability to save a gamestate" in {
        controller.s.nextState(Roll(controller))
        controller.getGameState should be (GameState(controller.getGameState.controller))
      }
      "notify its Observer after the cube is thrown which cells are possible to go" in {
        controller.getPCells(20, 5)
        observer.update should be()
      }
      "The Controller can set a players figure and con remove it " in {
        controller.setPlayer(1,22)
        controller.setFigure(1,22)
        controller.gameBoard.getCellList(22).playerNumber should be(1)
        controller.gameBoard.getCellList(22).figureNumber should be(1)
      }
      "The Controller can reset the the possible Cells of the actual turn" in {
        controller.resetPossibleCells
        controller.gameBoard.getPossibleCells should be (Set().empty)
      }
      "The Controller can save the Game" in {
        controller.save()
        controller.load()

        controller.gameBoard.getCellList(22).playerNumber should be(1)
      }
      "The Controller can set a List of Cell the Attribut possible Cell true" in {
        controller.setPosisCellTrue(List(30, 31, 32, 33))
        controller.gameBoard.getCellList(30).possibleCells should be(true)
        controller.gameBoard.getCellList(31).possibleCells should be(true)
        controller.gameBoard.getCellList(32).possibleCells should be(true)
        controller.gameBoard.getCellList(33).possibleCells should be(true)
      }
      "The Controller can set a List of Cell the Attribut possible Cell false" in {
        controller.setPosisCellFalse(List(30, 31, 32, 33))
        controller.gameBoard.getCellList(30).possibleCells should be(false)
        controller.gameBoard.getCellList(31).possibleCells should be(false)
        controller.gameBoard.getCellList(32).possibleCells should be(false)
        controller.gameBoard.getCellList(33).possibleCells should be(false)
      }
      "Reset the GameBoard" in {
        val a = controller.resetGameboard
        a shouldNot be (controller.getGameBoard)
      }
    }
  }
}

