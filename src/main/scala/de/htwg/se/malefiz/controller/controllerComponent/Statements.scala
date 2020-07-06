package de.htwg.se.malefiz.controller.controllerComponent

object Statements extends Enumeration with InstructionTrait {
      type Statements = Value
      val addPlayer ,roll ,selectFigure, selectField, wrongField, selectWrongFigure, nextPlayer, wall, wrongWall, won = Value

      val value: Handler2 = {
      case StatementRequest(z) if z.getStatement == roll => s"${z.getPlayersTurn} du bist als erstes dran. Klicke auf den Würfel!"
      case StatementRequest(z) if z.getStatement == addPlayer => "Spieler wurden erfolgreich angelegt."
      case StatementRequest(z) if z.getStatement == wrongField => "Nicht so schnell! Gehe bitte nur auf die markierten Felder!"
      case StatementRequest(z) if z.getStatement == selectFigure => s"${z.getPlayersTurn} du hast eine ${z.getDicedNumber} gewürfelt. Wähle deine gewünschte Figur aus!"
      case StatementRequest(z) if z.getStatement == selectField => "Du kannst nun auf folgende Felder gehen. Wähle ein markiertes Felder aus."
      case StatementRequest(z) if z.getStatement == selectWrongFigure => s"${z.getPlayersTurn} bitte wähle deine eigene Figur aus!"
      case StatementRequest(z) if z.getStatement == nextPlayer => s"${z.getPlayersTurn} du bist als nächstes dran. Klicke auf den Würfel!"
      case StatementRequest(z) if z.getStatement == wall => s"${z.getPlayersTurn} du bist auf eine Mauer gekommen. Lege Sie bitte um."
      case StatementRequest(z) if z.getStatement == wrongWall => s"${z.getPlayersTurn} du darfst die Mauer dort nicht setzen. Bitte wähle ein anderes Feld aus."
      case StatementRequest(z) if z.getStatement == won => s"Gratulation ${z.getPlayersTurn} du hast das Spiel gewonnen! "
    }
  }
