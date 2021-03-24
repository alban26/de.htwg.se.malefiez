package de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl

case class Cell(
    cellNumber: Int,
    playerNumber: Int,
    figureNumber: Int,
    wallPermission: Boolean,
    hasWall: Boolean,
    coordinates: Point,
    possibleFigures: Boolean,
    possibleCells: Boolean
) {

  /*
 type Select = PartialFunction[Int, String]

 val selects: List[Select] = List (
   { case x if cellNumber < 20 && playerNumber != 0 && !hasWall && possibleFigures =>  "("+ playerNumber+"|"+figureNumber+")"},
   { case x if cellNumber < 20 && playerNumber != 0 && !hasWall && !possibleFigures => "(" + playerNumber + ")"},
   { case x if cellNumber < 20 => "( )"},
   { case x if cellNumber >= 20 && playerNumber != 0 && !hasWall && possibleCells => "[" + playerNumber + "|" + cellNumber + "]" },
   { case x if cellNumber >= 20 && playerNumber != 0 && !hasWall  && !possibleCells => "[" + playerNumber + "]"},
   { case x if cellNumber >= 20 && playerNumber != 0 && hasWall  && possibleCells => "[X" + "|" + cellNumber + "]"},
   { case x if cellNumber >= 20 && playerNumber != 0 && hasWall && !possibleCells => "[X]"},
   { case x if cellNumber >= 20 && playerNumber != 0 && !hasWall && possibleCells => "[" + cellNumber + "]"},
   { case x if cellNumber >= 20 && playerNumber != 0 && !hasWall && !possibleCells => "[ ]"},
 )
 val stein_or_spieler: String = selects.tail.(_.orElse(_)) {cellNumber}
   */

  val stein_or_spieler: String =
    if (cellNumber < 20)
      if (playerNumber != 0 && !hasWall)
        if (possibleFigures)
          "(" + playerNumber + "|" + figureNumber + ")"
        else
          "(" + playerNumber + ")"
      else
        "( )"
    else if (playerNumber != 0 && !hasWall)
      if (possibleCells)
        "[" + playerNumber + "|" + cellNumber + "]"
      else
        "[" + playerNumber + "]"
    else if (hasWall)
      if (possibleCells)
        "[X" + "|" + cellNumber + "]"
      else
        "[X]"
    else if (possibleCells)
      "[" + cellNumber + "]"
    else
      "[ ]"

  override def toString: String = stein_or_spieler

}
