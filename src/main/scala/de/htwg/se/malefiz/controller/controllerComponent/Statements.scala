package de.htwg.se.malefiz.controller.controllerComponent

object Statements extends Enumeration with InstructionTrait {

  type Statements = Value
  val addPlayer, roll, selectFigure, selectField, wrongField, selectWrongFigure, nextPlayer, wall, wrongWall, won, changeFigure = Value

  val value: Handler2 = {
    case StatementRequest(controller) if controller.getStatement ==
      roll => s"${controller.getPlayersTurn.get} du bist als erstes dran. Klicke auf den Würfel!"
    case StatementRequest(controller) if controller.getStatement ==
      addPlayer => "Spieler wurden erfolgreich angelegt."
    case StatementRequest(controller) if controller.getStatement ==
      wrongField => "Nicht so schnell! Gehe bitte nur auf die markierten Felder!"
    case StatementRequest(controller) if controller.getStatement ==
      selectFigure => s"${controller.getPlayersTurn.get} du hast eine ${controller.getDicedNumber} gewürfelt. Wähle deine gewünschte Figur aus!"
    case StatementRequest(controller) if controller.getStatement ==
      selectField => "Du kannst nun auf folgende Felder gehen. Wähle ein markiertes Felder aus."
    case StatementRequest(controller) if controller.getStatement ==
      selectWrongFigure => s"${controller.getPlayersTurn.get} bitte wähle deine eigene Figur aus!"
    case StatementRequest(controller) if controller.getStatement ==
      nextPlayer => s"${controller.getPlayersTurn.get} du bist als nächstes dran. Klicke auf den Würfel!"
    case StatementRequest(controller) if controller.getStatement ==
      wall => s"${controller.getPlayersTurn.get} du bist auf eine Mauer gekommen. Lege Sie bitte um."
    case StatementRequest(controller) if controller.getStatement ==
      wrongWall => s"${controller.getPlayersTurn.get} du darfst die Mauer dort nicht setzen. Bitte wähle ein anderes Feld aus."
    case StatementRequest(controller) if controller.getStatement ==
      won => s"Gratulation ${controller.getPlayersTurn.get} du hast das Spiel gewonnen! "
    case StatementRequest(controller) if controller.getStatement ==
      changeFigure => s"${controller.getPlayersTurn.get} du hast deine Figur abgewählt. Wähle bitte erneut eine Figur aus!"
  }

}
