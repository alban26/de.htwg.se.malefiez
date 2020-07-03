package de.htwg.se.malefiz.controller.controllerComponent.Instructions
import de.htwg.se.malefiz.controller.controllerComponent
import de.htwg.se.malefiz.controller.controllerComponent.controllerBaseImpl.Controller
class Statement(controller: Controller) extends Enumeration {

  type Statement = Value
  val addPlayer ,roll ,selectFigure, selectField, selectWrongFigure, nextPlayer, wall, wrongWall   = Value

    val map = Map[Statement, String](
      addPlayer -> s"Spieler wurde erfolgreich angelegt.",
      roll -> s"Lieber ${controller.playersTurn} du bist als erstes dran. Drücke eine beliebige Taste um zu würfeln!",
      selectFigure -> s"Du hast eine ${controller.dicedNumber} gewürfelt. Wähle nun deine gewünschte Figur aus.",
      selectField -> s"Du kannst nun auf folgende Felder gehen. Wähle eine aus indem du die Nummer eintippst.",
      selectWrongFigure -> s"Lieber ${controller.playersTurn} bitte wähle deine eigene Figur aus.",
      nextPlayer -> s"Lieber ${controller.playersTurn} du bist als nächstes dran. Drücke eine beliebige Taste um zu würfeln.",
      wall -> s"Lieber ${controller.playersTurn} du bist auf eine Mauer gekommen. Lege Sie bitte um.",
      wrongWall -> s"Lieber ${controller.playersTurn} du darfst die Mauer dort nicht setzen. Bitte wähle ein anderes Feld aus."
    )

}
