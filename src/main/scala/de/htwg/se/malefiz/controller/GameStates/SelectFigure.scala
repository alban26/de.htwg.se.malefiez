package de.htwg.se.malefiz.controller.GameStates

import de.htwg.se.malefiz.controller.{Controller, State}

case class SelectFigure(controller: Controller) extends State[GameState] {
  override def handle(string: String, n: GameState): Unit = {
    string.toList.filter(c => c != ' ').filter(_.isDigit).map(c => c.toString.toInt) match {
      case player :: figure :: Nil => controller.getPCells(controller.getFigure(player, figure), controller.dicedNumber)
        controller.selectedFigure = (player,figure)
        controller.setPosisCellTrue(controller.gameBoard.possibleCells.toList)
        println("Du kannst nun auf folgende Felder gehen. Wähle eine aus indem du die Nummer eintippst.")
        n.nextState(new SetFigure(controller))
    }

  }
}
