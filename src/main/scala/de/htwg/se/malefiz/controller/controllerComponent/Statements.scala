package de.htwg.se.malefiz.controller.controllerComponent

object Statements extends Enumeration {

    type Statements = Value
    val addPlayer ,roll ,selectFigure, selectField, wrongField, selectWrongFigure, nextPlayer, wall, wrongWall, won = Value

    val map = Map[Statements, String](
      addPlayer -> "Spieler wurden erfolgreich angelegt.",
      roll -> "Lieber du bist als erstes dran. Drücke eine beliebige Taste um zu würfeln!",
      selectFigure -> "Du hast eine gewürfelt. Wähle nun deine gewünschte Figur aus.",
      selectField -> "Du kannst nun auf folgende Felder gehen. Wähle eine aus indem du die Nummer eintippst.",
      selectWrongFigure -> "Lieber bitte wähle deine eigene Figur aus.",
      wrongField -> "Nicht so schnell! Gehe bitte nur auf die markierten Felder!",
      nextPlayer -> "Lieber du bist als nächstes dran. Drücke eine beliebige Taste um zu würfeln.",
      wall -> "Lieber du bist auf eine Mauer gekommen. Lege Sie bitte um.",
      wrongWall -> "Lieber du darfst die Mauer dort nicht setzen. Bitte wähle ein anderes Feld aus.",
      won -> "Herzlichen Glückwunsch du hast das Spiel gewonnen! ."
    )

     def message(statements: Statements) = {
      map(statements)
     }
  }
