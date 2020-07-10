package de.htwg.se.malefiz.model.fileIoCmponent

import de.htwg.se.malefiz.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl.GameBoard
import org.scalatest.{Matchers, WordSpec}

class FileIOSpec extends WordSpec with Matchers {

  "FilIO" when {
    "called playing a Game" should {

      var gameBoard = new GameBoard()
      var controller = new Controller(gameBoard)

      controller.createPlayer("Robert")
      controller.createPlayer("Alban")
      controller.execute("n Robert")
      controller.execute("n Alban")
      controller.execute("start")

      controller.setStateNumber(1)
      controller.setSelectedFigures(1, 1)

      "save and load with XML" in {
        import de.htwg.se.malefiz.model.fileIoComponent.fileIoXmlImpl.FileIO
        val fileIO = new FileIO()
        fileIO.save(gameBoard,controller)
        fileIO.load should be(gameBoard)
        //fileIO.load.getPlayer.head.playerNumber should be (1)
        //fileIO.load.getPlayer.head.name should be ("Robert")
      }
      "save and load with Json" in {
        import de.htwg.se.malefiz.model.fileIoComponent.fileIoJsonImpl.FileIO
        val fileIO = new FileIO()
        fileIO.save(gameBoard, controller)
        fileIO.load should be(gameBoard)
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
