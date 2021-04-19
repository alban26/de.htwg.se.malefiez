package de.htwg.se.malefiz.gameBoardModule.model.gameBoardComponent.gameBoardBaseImpl

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

  val stein_or_spieler: String =
    if (cellNumber < 20)
      if (playerNumber != 0 && !hasWall)
        if (possibleFigures)
          "" + playerNumber + "|" + figureNumber +" "
        else
          playerNumber+""
      else
        "_"
    else if (playerNumber != 0 && !hasWall)
      if (possibleCells)
        ""+playerNumber + "|" + cellNumber+""
      else
        playerNumber+""
    else if (hasWall)
      if (possibleCells)
        "X" + "|" + cellNumber
      else
        "X"
    else if (possibleCells)
      cellNumber+""
    else
      "_"

  override def toString: String = stein_or_spieler

}
