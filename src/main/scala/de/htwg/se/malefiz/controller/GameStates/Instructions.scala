package de.htwg.se.malefiz.controller.GameStates

import de.htwg.se.malefiz.controller.Controller


object Instructions {

  type Handler = PartialFunction[Request, String]

  val setup1: Handler = {
    case Request(x, y, z) if x.contains("start") || z.gameBoard.players.length == 4 =>
      z.playersTurn = z.gameBoard.players(0)
      y.nextState(new Roll(z))
      "Lieber " + z.playersTurn + " du bist als erstes dran. Drücke eine beliebige Taste um zu würfeln."
  }

  val setup2: Handler = {
    case Request(x, y, z) =>
      z.createPlayer(x(1))
      s"Spieler ${x(1)} wurde erfolgreich angelegt."
  }

  val log = (message: String) => {
    println(message)
    message
  }

  val setup = (setup1 andThen log) orElse (setup2 andThen log)
}

