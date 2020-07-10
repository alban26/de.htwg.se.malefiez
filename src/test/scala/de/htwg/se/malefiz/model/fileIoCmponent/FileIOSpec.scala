package de.htwg.se.malefiz.model.fileIoCmponent

import de.htwg.se.malefiz.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl.GameBoard
import org.scalatest.{Matchers, WordSpec}

class FileIOSpec extends WordSpec with Matchers {

  "FilIO" when {
    "called playing a Game" should {

      var playfield = new GameBoard()
      var controller = new Controller(playfield)
      controller.gameBoard.createPlayer("Robert")
      controller.createPlayer("Lukas")
      controller.execute("start")
      controller.setStateNumber(1)
      controller.setSelectedFigures(1, 1)

      "save and load with XML" in {
        import de.htwg.se.malefiz.model.fileIoComponent.fileIoXmlImpl.FileIO
        val fileIO = new FileIO()
        fileIO.save(playfield,controller)
        fileIO.load should be(playfield)
      }
      "save and load with Json" in {
        import de.htwg.se.malefiz.model.fileIoComponent.fileIoJsonImpl.FileIO
        val fileIO = new FileIO()
        fileIO.save(playfield, controller)
        fileIO.load should be(playfield)
      }
    }

  }

}
