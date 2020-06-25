package de.htwg.se.malefiz.controller.Instructions
/*
import de.htwg.se.malefiz.controller.GameStates.{Request, Roll, SelectFigure, SetFigure}

object Instructions  {

  //type Handler = PartialFunction[Option[Request], String]
  type Handler0 = PartialFunction[Option[Request],Option[Request]]
  type Handler1 = PartialFunction[Option[Request],String]

  val setup1: Handler0 = {
    case Some(Request(x,y,z)) if x.contains("start") || z.gameBoard.players.length == 4 => z.playersTurn = z.gameBoard.players(0)
      Some(Request(x,y,z))
  }

  val setup2: Handler1 = {
    case Some(Request(x,y,z)) => y.nextState(Roll(z))
      s"Lieber ${z.playersTurn} du bist als erstes dran. Drücke eine beliebige Taste um zu würfeln!"
  }

  val setup3: Handler1 = {
    case Some(Request(x, y, z)) => z.createPlayer(x(1))
      s"Spieler ${x(1)} wurde erfolgreich angelegt."
  }

  val setupErr: Handler1 = {
    case None => "Einlesen des Spielers nicht erfolgt."
  }

  val setup = setup1 andThen setup2 orElse setup3 andThen log orElse (setupErr andThen log)



  val roll1: Handler0 = {
    case Some(Request(x,y,z)) if x != ' ' => z.dicedNumber = z.rollCube
      Some(Request(x,y,z))
  }

  val roll2: Handler0 = {
    case Some(Request(x,y,z)) => z.setPosisTrue(z.playersTurn.playerNumber)
      Some(Request(x,y,z))
  }

  val roll3: Handler1 = {
    case Some(Request(x,y,z)) => y nextState SelectFigure(z)
    s"Du hast eine ${z.dicedNumber} gewürfelt. Wähle nun deine gewünschte Figur aus."
  }

  val rollErr: Handler1 = {
    case None => "Beim würfeln ist ein Fehler aufgetreten"
  }

  val roll = roll1 andThen roll2 andThen roll3 andThen log orElse (rollErr andThen log)



  val select1: Handler0 = {
    case Some(Request(x, y, z)) if x.length == 2 => z.getPCells(z.getFigure(x(0).toInt, x(1).toInt), z.dicedNumber)
      Some(Request(x,y,z))
  }

  val select2: Handler0 = {
    case Some(Request(x, y, z)) => z.selectedFigure = (x(0).toInt, x(1).toInt)
      Some(Request(x,y,z))
  }

  val select3: Handler0 = {
    case Some(Request(x, y, z)) =>
      z.setPosisCellTrue(z.gameBoard.possibleCells.toList)
      Some(Request(x,y,z))
  }

  val select4: Handler1 = {
    case Some(Request(x,y,z)) =>
      y nextState SetFigure(z)
      "Du kannst nun auf folgende Felder gehen. Wähle eine aus indem du die Nummer eintippst."
  }

  val selectErr: Handler1 = {
    case None => "Bei Spielerauswahl fehler aufgetreten"
  }

  val select = select1 andThen select2 andThen select3 andThen select4 andThen log orElse (selectErr andThen log)


  val set1: Handler0 = {
    case Some(Request(x, y, z)) if x != ' ' =>
      z.setPlayerFigure(z.selectedFigure._1,z.selectedFigure._2,x(0).toInt)
      Some(Request(x,y,z))
  }

  val set2: Handler1 = {
    case Some(Request(x, y, z)) =>
      y.nextState(Roll(z))
      s"Lieber ${z.playersTurn} du bist als nächstes dran. Drücke eine beliebige Taste um zu würfeln."
  }

  val setErr: Handler1 = {
    case None => "Bei Spielerauswahl fehler aufgetreten"
  }

  val set = set1 andThen set2 andThen log orElse (setErr andThen log)

}
*/