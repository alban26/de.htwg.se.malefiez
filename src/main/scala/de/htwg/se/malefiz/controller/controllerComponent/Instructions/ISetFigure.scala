package de.htwg.se.malefiz.controller.controllerComponent.Instructions


import de.htwg.se.malefiz.controller.controllerComponent.GameStates.{Roll, SetStone}
import de.htwg.se.malefiz.controller.controllerComponent.{InstructionTrait, Request}
import de.htwg.se.malefiz.controller.controllerComponent

object ISetFigure extends InstructionTrait{
  val set1: Handler0 = {
    case Request(x, y, z) if x != ' ' && !z.gameBoard.cellList(x.head.toInt).hasWall =>
      z.setPlayerFigure(z.selectedFigure._1,z.selectedFigure._2,x.head.toInt)
      Request(x,y,z)
  }

  val set2: Handler0 = {
    case Request(x, y, z) if x != ' ' && z.gameBoard.cellList(x.head.toInt).hasWall =>
      z.setPlayerFigure(z.selectedFigure._1,z.selectedFigure._2,x.head.toInt)
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
      y.nextState(SetStone(z))
      s"Lieber ${z.playersTurn} du bist auf eine Mauer gekommen. Lege Sie bitte um."
  }


  val set = (set1 andThen set3 andThen log) orElse (set2 andThen set4 andThen log) // orElse (setErr andThen log)
}
