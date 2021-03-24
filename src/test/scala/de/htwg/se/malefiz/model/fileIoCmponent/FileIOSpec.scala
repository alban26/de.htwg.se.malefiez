package de.htwg.se.malefiz.model.fileIoCmponent

import de.htwg.se.malefiz.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl.GameBoard
import org.scalatest.matchers.should.Matchers
import org.scalatest._

class FileIOSpec extends WordSpec with Matchers {

  "FilIO" when {
    "called playing a Game" should {
      "save and load with XML" +
        "1. We simulate a new game with the controller variable" +
        "2. We read two player A and B to the game" +
        "3. We start the game with start" +
        "4. Player A can now throw the Dice -> r" +
        "5. We manually set the thrown number to 1 so we can better test" +
        "6. We chose the first figure of player A" +
        "7. In the FileIO Class we create a new controller that fills the current controller" +
        "with the new data from the read file" in {

        import de.htwg.se.malefiz.model.fileIoComponent.fileIoXmlImpl.FileIO
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
        fileIO.load.getPossibleCells.head should be(22)
        controller.getPlayersTurn.get.name should be("A")

      }
      "save and load with Json" +
        "1. We simulate a new game with the controller variable" +
        "2. We read two player A and B to the game" +
        "3. We start the game with start" +
        "4. Player A can now throw the Dice -> r" +
        "5. We manually set the thrown number to 1 so we can better test" +
        "6. We chose the first figure of player A" +
        "7. In the FileIO Class we create a new controller that fills the current controller" +
        "with the new data from the read file" in {

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
        fileIO.load.getPossibleCells.head should be(22)
        controller.getPlayersTurn.get.name should be("A")
        controller.gameBoard.dicedNumber should be(1)

      }
    }
  }
}
