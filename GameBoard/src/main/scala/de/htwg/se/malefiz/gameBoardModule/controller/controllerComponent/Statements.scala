package de.htwg.se.malefiz.gameBoardModule.controller.controllerComponent

object Statements extends Enumeration with InstructionTrait {

  type Statements = Value
  val addPlayer, roll, selectFigure, selectField, wrongField, selectWrongFigure, nextPlayer, wall, wrongWall, won,
  changeFigure = Value

  val value: Handler2 = {
    case StatementRequest(controller) if controller.gameBoard.statementStatus.get == roll =>
      s"${controller.gameBoard.playersTurn.get} du bist als erstes dran. Klicke auf den Würfel!"
    case StatementRequest(controller)
      if controller.gameBoard.statementStatus.get ==
        addPlayer =>
      "Spieler wurden erfolgreich angelegt."
    case StatementRequest(controller)
      if controller.gameBoard.statementStatus.get ==
        wrongField =>
      "Nicht so schnell! Gehe bitte nur auf die markierten Felder!"
    case StatementRequest(controller)
      if controller.gameBoard.statementStatus.get ==
        selectFigure =>
      s"${controller.gameBoard.playersTurn.get} du hast eine ${controller.gameBoard.dicedNumber.get} gewürfelt. " +
        s"Wähle deine gewünschte Figur aus!"
    case StatementRequest(controller)
      if controller.gameBoard.statementStatus.get ==
        selectField =>
      "Du kannst nun auf folgende Felder gehen. Wähle ein markiertes Felder aus."
    case StatementRequest(controller)
      if controller.gameBoard.statementStatus.get ==
        selectWrongFigure =>
      s"${controller.gameBoard.playersTurn.get} bitte wähle deine eigene Figur aus!"
    case StatementRequest(controller)
      if controller.gameBoard.statementStatus.get ==
        nextPlayer =>
      s"${controller.gameBoard.playersTurn.get} du bist als nächstes dran. Klicke auf den Würfel!"
    case StatementRequest(controller)
      if controller.gameBoard.statementStatus.get ==
        wall =>
      s"${controller.gameBoard.playersTurn.get} du bist auf eine Mauer gekommen. Lege Sie bitte um."
    case StatementRequest(controller)
      if controller.gameBoard.statementStatus.get ==
        wrongWall =>
      s"${controller.gameBoard.playersTurn.get} du darfst die Mauer dort nicht setzen. Bitte wähle ein anderes Feld aus."
    case StatementRequest(controller)
      if controller.gameBoard.statementStatus.get ==
        won =>
      s"Gratulation ${controller.gameBoard.playersTurn.get} du hast das Spiel gewonnen! "
    case StatementRequest(controller)
      if controller.gameBoard.statementStatus.get ==
        changeFigure =>
      s"${controller.gameBoard.playersTurn.get} du hast deine Figur abgewählt. Wähle bitte erneut eine Figur aus!"
  }

}
