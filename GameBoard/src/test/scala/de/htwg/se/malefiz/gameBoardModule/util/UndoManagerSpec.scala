package de.htwg.se.malefiz.gameBoardModule.util

import org.scalatest._
import org.scalatest.matchers.should.Matchers
/*
class UndoManagerSpec extends WordSpec with Matchers {

  "An UndoManager" should {
    val undoManager = new UndoManager

    "have a do, undo and redo" in {
      val command = new incrCommand
      command.state should be(0)
      undoManager.doStep(command)
      command.state should be(1)
      undoManager.undoStep()
      command.state should be(0)
      undoManager.redoStep()
      command.state should be(1)
    }

    "handle multiple undo steps correctly" in {
      val command = new incrCommand
      command.state should be(0)
      undoManager.doStep(command)
      command.state should be(1)
      undoManager.doStep(command)
      command.state should be(2)
      undoManager.undoStep()
      command.state should be(1)
      undoManager.undoStep()
      command.state should be(0)
      undoManager.redoStep()
      command.state should be(1)
    }
  }

}


 */