package de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent.controllerBaseImpl

import scala.collection.mutable.Map
import de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent.GameStates.{Roll, SelectFigure}
import de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent.Statements.addPlayer
import de.htwg.se.malefiz.gameBoardModule.model.gameBoardComponent.gameBoardBaseImpl.{Cell, Creator, GameBoard, Player}
import org.scalatest._
import org.scalatest.matchers.should.Matchers

class ControllerSpec extends WordSpec with Matchers {
  "A Controller" when {

    "observed by an Observer" should {
      val cellConfigFile = "/configuration/mainCellConfiguration"
      val cellLinksFile = "/configuration/mainCellLinks"
      val players: List[Option[Player]] = List().empty
      val cellList: List[Cell] = Creator().getCellList(cellConfigFile)
      val cellGraph: Map[Int, Set[Int]] = Creator().getCellGraph(cellLinksFile)

      val controller = new Controller(
        GameBoard(
          cellList,
          players,
          cellGraph,
          Set().empty,
          Option(1),
          None,
          None,
          None,
          Option(addPlayer)))

      controller.setPlayersTurn(Option.apply(Player(1, "Robert")))

      "Test undo -> Nil case" in {
        controller.undo() should be(())
      }
      "Set Players" in {
        controller.createPlayer("Robert")
        controller.createPlayer("Alban")

        controller.gameBoard.players.head.get.name should be("Robert")
        controller.gameBoard.players(1).get.name should be("Alban")
      }
      "notify its Observer after a players figure is set on cell" in {
        controller.placePlayerFigure(1, 1, 10)
        controller.getFigurePosition(1, 1) should be(10)
        controller.removeActualPlayerAndFigureFromCell(1, 1)
        controller.gameBoard.cellList(10).playerNumber should be(0)
      }
      "notify its Observer after setting a Wall on a Cell" in {
        controller.placeOrRemoveWall(50, boolean = true)
        controller.gameBoard.cellList(50).hasWall should be(true)
      }
      "The Controller has the ability to remove a wall" in {
        controller.placeOrRemoveWall(50, boolean = false)
        controller.gameBoard.cellList(50).hasWall should be(false)
      }
      "The Controller has the ability to save the current selected figure of a player" in {
        controller.setSelectedFigure(1, 5)
        controller.gameBoard.selectedFigure.get should be((1, 5))
      }
      "The Controller has the ability to save a gameState" in {
        controller.state.nextState(Roll(controller))
        controller.getGameState.currentState.toString should be("1")
      }
      "notify its Observer after the cube is thrown which cells are possible to go" in {
        controller.calculatePath(20, 5)
      }
      "The Controller can set a players figure and con remove it " in {
        controller.placePlayer(1, 22)
        controller.placeFigure(1, 22)
        controller.gameBoard.cellList(22).playerNumber should be(1)
        controller.gameBoard.cellList(22).figureNumber should be(1)
      }
      "The controller can undo the last command" in {
        val a = new SetPlayerCommand(2, 2, 30, controller)
        controller.undo()
        controller.gameBoard.cellList(22).playerNumber should be(0)
        controller.gameBoard.cellList(22).figureNumber should be(0)

      }
      "The Controller can reset the the possible Cells of the actual turn" in {
        controller.resetPossibleCells()
        controller.gameBoard.possibleCells should be(Set().empty)
      }
      "The Controller can set the attribute 'possibleCells' true" in {
        controller.state.currentState = SelectFigure(controller)
        controller.setPossibleCellsTrueOrFalse(List(30, 31, 32, 33))
        controller.gameBoard.cellList(30).possibleCells should be(true)
        controller.gameBoard.cellList(31).possibleCells should be(true)
        controller.gameBoard.cellList(32).possibleCells should be(true)
        controller.gameBoard.cellList(33).possibleCells should be(true)
      }
      "The Controller can set a List of Cell the Attribute possible Cell false" in {
        controller.state.currentState = Roll(controller)
        controller.setPossibleCellsTrueOrFalse(List(30, 31, 32, 33))
        controller.gameBoard.cellList(30).possibleCells should be(false)
        controller.gameBoard.cellList(31).possibleCells should be(false)
        controller.gameBoard.cellList(32).possibleCells should be(false)
        controller.gameBoard.cellList(33).possibleCells should be(false)
      }
      "Reset the GameBoard" in {
        val newGameBoard: Unit = controller.resetGameBoard()
        newGameBoard shouldNot be(controller.gameBoard)
      }
    }
  }
}



