package de.htwg.se.malefiz.controller.controllerComponent.Instructions


import de.htwg.se.malefiz.controller.controllerComponent.GameStates.{Roll, SetStone, Setup}
import de.htwg.se.malefiz.controller.controllerComponent.{InstructionTrait, Request}
import de.htwg.se.malefiz.controller.controllerComponent

object ISetFigure extends InstructionTrait{
  val set1: Handler0 = {
    case Request(x, y, z) if !z.getCellList(x.head.toInt).hasWall && (x.head.toInt != 131) =>
      z.setPlayerFigure(z.selectedFigure._1,z.selectedFigure._2,x.head.toInt)
      Request(x,y,z)
  }

  val setF: Handler0 = {
    case Request(x, y, z) if x.head.toInt == 131 =>
      Request(x, y, z)
  }

  val setA: Handler1 = {
    case Request(x, y, z) =>
      y.nextState(Setup(z))
      z.resetGameboard()
      z.weHaveAWinner
      s"Herzlichen Glückwunsch ${z.playersTurn} du hast das Spiel gewonnen! ."
  }
// hallo
  val set2: Handler0 = {
    case Request(x, y, z) if z.getCellList(x.head.toInt).hasWall =>
      z.setPlayerFigure(z.selectedFigure._1,z.selectedFigure._2,x.head.toInt)
      controllerComponent.Request(x,y,z)
  }

  val set3: Handler1 = {
    case Request(x, y, z) =>
      z.dicedNumber = 0
      z.setPosisFalse(z.playersTurn.playerNumber)
      z.setPosisCellFalse(z.getPossibleCells.toList)
      z.playersTurn = z.gameBoard.nextPlayer(z.getPlayer,z.playersTurn.playerNumber-1)
      y.nextState(Roll(z))
      s"Lieber ${z.playersTurn} du bist als nächstes dran. Drücke eine beliebige Taste um zu würfeln."
  }

  val set4: Handler1 = {
    case Request(x, y, z) =>
      y.nextState(SetStone(z))
      s"Lieber ${z.playersTurn} du bist auf eine Mauer gekommen. Lege Sie bitte um."
  }


  val set: PartialFunction[Request, String] = (set1 andThen set3 andThen log) orElse (set2 andThen set4 andThen log) orElse (setF andThen setA andThen log)
}
