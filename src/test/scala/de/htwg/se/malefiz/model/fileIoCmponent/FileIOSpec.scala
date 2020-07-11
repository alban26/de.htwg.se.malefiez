package de.htwg.se.malefiz.model.fileIoCmponent

import de.htwg.se.malefiz.aview.Tui
import de.htwg.se.malefiz.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl.{Cell, Creator, GameBoard}
import de.htwg.se.malefiz.model.playerComponent.Player
import org.scalatest.matchers.should.Matchers
import org.scalatest._

import scala.collection.mutable.Map

class FileIOSpec extends WordSpec with Matchers {

  "FilIO" when {
    "called playing a Game" should {
/*
      val cellConfigFile = "project/mainCellConfiguration"
      val cellLinksFile = "project/mainCellLinks"

      val players: List[Player] = List().empty
      val cellList: List[Cell] = Creator().getCellList(cellConfigFile)
      val cellGraph: Map[Int, Set[Int]] = Creator().getCellGraph(cellLinksFile)
      val possibleCells: Set[Int] = Set().empty

      var gameBoard = GameBoard(cellList, players, cellGraph, possibleCells)

      val controller = new Controller(gameBoard)
      val tui = new Tui(controller)

      tui.processInput1("n A")
      tui.processInput1("n B")
      tui.processInput1("start")

      tui.processInput1("r")
      controller.setDicedNumber(1)
      tui.processInput1("1 1")
*/

      "save and load with XML" in {
        import de.htwg.se.malefiz.model.fileIoComponent.fileIoXmlImpl.FileIO
        val controller = new Controller(new GameBoard())
        controller.execute("n A")
        controller.execute("n B")
        controller.execute("start")
        controller.execute("r")
        controller.setDicedNumber(1)
        controller.execute("1 1")
        val fileIO = new FileIO()

        fileIO.loadController shouldNot be (controller)
        fileIO.save(controller.gameBoard,controller)

        fileIO.load.getPlayer.head.name should be("A")
        fileIO.load.getPossibleCells.head should be (22)
        controller.playersTurn.name should be ("A")
        //controller.getDicedNumber should be (1)
        //fileIO.load.getPlayer.head.playerNumber should be (1)
        //fileIO.load.getPlayer.head.name should be ("Robert")
      }
      "save and load with Json" in {

        import de.htwg.se.malefiz.model.fileIoComponent.fileIoJsonImpl.FileIO
        val controller = new Controller(new GameBoard())
        controller.execute("n A")
        controller.execute("n B")
        controller.execute("start")
        controller.execute("r")
        controller.setDicedNumber(1)
        controller.execute("1 1")
        val fileIO = new FileIO()

        fileIO.save(controller.gameBoard, controller)
        fileIO.load.getPlayer.head.name should be("A")
        fileIO.load.getPossibleCells.head should be (22)
        controller.playersTurn.name should be ("A")
        controller.getDicedNumber should be (1)
        /*
        fileIO.load.getPlayer.head.playerNumber should be (1)
        fileIO.load.getPlayer.head.name should be ("Robert")
        fileIO.load.getPlayer(2).playerNumber should be (2)
        fileIO.load.getPlayer(2).name should be ("Alban")

         */
      }
    }

  }

}
