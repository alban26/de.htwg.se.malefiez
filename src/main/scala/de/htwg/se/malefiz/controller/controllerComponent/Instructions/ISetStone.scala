package de.htwg.se.malefiz.controller.controllerComponent.Instructions

import de.htwg.se.malefiz.controller
import de.htwg.se.malefiz.controller.controllerComponent.GameStates.{Roll, SetStone}
import de.htwg.se.malefiz.controller.{InstructionTrait, Request, controllerComponent}
import de.htwg.se.malefiz.controller.controllerComponent.Instructions.ISetFigure.{Handler0, Handler1}
import de.htwg.se.malefiz.controller.controllerComponent.{InstructionTrait, Request}

object ISetStone extends InstructionTrait {

  val set1: Handler0 = {
    case Request(x, y, z) if z.gameBoard.cellList(x.head.toInt).wallPermission && z.gameBoard.cellList(x.head.toInt).playerNumber == 0 =>
      z.setWall(x.head.toInt)
      Request(x,y,z)
  }

  val set2: Handler0 = {
    case Request(x, y, z) if !z.gameBoard.cellList(x.head.toInt).wallPermission || z.gameBoard.cellList(x.head.toInt).playerNumber != 0 =>
      controllerComponent.Request(x,y,z)
  }

  val set3: Handler1 = {
    case Request(x, y, z) =>
      z.dicedNumber = 0
      z.setPosisFalse(z.playersTurn.playerNumber)
      z.setPosisCellFalse(z.gameBoard.possibleCells.toList)
      z.playersTurn = z.gameBoard.nextPlayer(z.gameBoard.players,z.playersTurn.playerNumber-1)
      y.nextState(Roll(z))
      s"Lieber ${z.playersTurn} du bist als nächstes dran. Drücke eine beliebige Taste um zu würfeln."
  }

  val set4: Handler1 = {
    case Request(x, y, z) =>
      s"Lieber ${z.playersTurn} du darfst die Mauer dort nicht setzen. Bitte wähle ein anderes Feld aus."
  }

  val set = (set1 andThen set3 andThen log) orElse (set2 andThen set4 andThen log)

}
