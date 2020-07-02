package de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl

import com.google.inject.Inject

case class Cell (cellNumber: Int, playerNumber: Int, figureNumber: Int, destination: Boolean, wallPermission: Boolean, hasWall: Boolean,
                coordinates: Point, possibleFigures: Boolean, possibleCells: Boolean) {

/*
 type Select = PartialFunction[Int, String]

 val selects: List[Select] = List (
   { case x if cellNumber < 20 && playerNumber != 0 && !hasWall && possibleFigures =>  "("+ playerNumber+"|"+figureNumber+")"},
   { case x if cellNumber < 20 && playerNumber != 0 && !hasWall && !possibleFigures => "(" + playerNumber + ")"},
   { case x if cellNumber < 20 && playerNumber == 0 && !hasWall => "( )"},
   { case x if cellNumber >= 20 && playerNumber != 0 && !hasWall => "["  + playerNumber + "]" },
   { case x if cellNumber >= 20 && playerNumber == 0 && hasWall => "[x]"},
   { case x if cellNumber >= 20 && playerNumber == 0 && !hasWall => "[ ]"}
 )

 val stein_or_spieler: String = selects.tail.foldLeft(selects.head)(_.orElse(_)) {6}

*/


val stein_or_spieler =
  if (cellNumber < 20) {
    if (playerNumber != 0 && !hasWall) {
      if (possibleFigures) {
        "(" + playerNumber + "|" + figureNumber + ")"
      } else {
        "(" + playerNumber + ")"
      }
    } else {
      "( )"
    }
  } else {
    if (playerNumber != 0 && !hasWall) {
      if (possibleCells) {
        "[" + playerNumber + "|" + cellNumber + "]"
      } else {
        "[" + playerNumber + "]"
      }
    } else if (hasWall) {
      if (possibleCells) {
        "[X" + "|" + cellNumber + "]"
      } else {
        "[X]"
      }
    } else {
      if (possibleCells) {
        "[" + cellNumber + "]"
      } else {
        "[ ]"
      }
    }
  }


override def toString: String = stein_or_spieler

}
