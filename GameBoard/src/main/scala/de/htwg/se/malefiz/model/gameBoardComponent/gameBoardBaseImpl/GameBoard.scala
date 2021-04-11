package de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl

import de.htwg.se.malefiz.Malefiz.{cellConfigFile, cellLinksFile}
import de.htwg.se.malefiz.controller.controllerComponent.Statements.{Statements, addPlayer}
import de.htwg.se.malefiz.model.gameBoardComponent.GameBoardInterface
import de.htwg.se.malefiz.model.playerComponent.Player

import scala.collection.mutable.Map

case class GameBoard(cellList: List[Cell],
                     players: List[Option[Player]],
                     gameBoardGraph: Map[Int, Set[Int]],
                     possibleCells: Set[Int] = Set.empty,
                     dicedNumber: Option[Int],
                     playersTurn: Option[Player],
                     selectedFigure: Option[(Int, Int)],
                     stateNumber: Option[Int],
                     statementStatus: Option[Statements]
                    ) extends GameBoardInterface {

  def this() = this(
    Creator().getCellList(cellConfigFile),
    List.empty,
    Creator().getCellGraph(cellLinksFile),
    Set.empty,
    Option.empty,
    None,
    None,
    None,
    Option(addPlayer)
  )

  //--- Spieler
  override def buildPlayerString(): Option[String] = {
    Some(s"""|${cellList.slice(0,20).mkString("")}
             |""".stripMargin)
  }


  //--- Gameboard
  val buildRow = (start: Int, stop: Int) => start match {
    case 130 => s"""|${cellList.slice(start, stop).mkString("")}
                    |""".stripMargin
    case _ => buildGameBoardString(stop) + s"""|${cellList.slice(start, stop).mkString("")}
                                               |""".stripMargin
  }

  override def buildGameBoardString(start: Int): String = {
    start match {
      case 20 | 42 | 94 | 113 => buildRow(start, start + 17)
      case 37 => buildRow(start, start + 5)
      case 59 => buildRow(start, start + 4)
      case 63 => buildRow(start, start + 13)
      case 76 | 87 | 111 => buildRow(start, start + 2)
      case 78 => buildRow(start, start + 9)
      case 89 => buildRow(start, start + 5)
      case 93 => buildRow(start, start + 1)
      case 130 => buildRow(start, start + 1)
    }
  }

  override def buildGameBoard(): Option[String] = Some(buildGameBoardString(20))

  override def buildGameBoardInfo(): Option[String] = {
    val string = new StringBuilder("Malefiz-GameBoard\n\n")
    string.append("Players: ")
    players.flatMap(player => player.map(x => string.append(x.toString + " / ")))
    string.append("\n" + "Players turn: " + playersTurn.getOrElse("No registered players").toString + "\n")
    string.append("Dice: " + dicedNumber.getOrElse("Dice is not rolled yet").toString + "\n")
    string.append("Status: " + statementStatus.get.toString + "\n")
    Some(string.toString())
  }


  override def buildCompleteBoard(cellList: List[Cell]): Option[String] = {
    /*
    val a = buildGameboard().flatMap(x => buildPlayerString()
    .flatMap(y => buildGameboardInfo().map(z => x + y + z)))
     */
    for {
      gameBoardString <- buildGameBoard()
      playerString <- buildPlayerString()
      infoString <- buildGameBoardInfo()
    } yield gameBoardString + playerString + infoString
  }


  override def setPlayersTurn(player: Option[Player]): GameBoard = copy(playersTurn = player)

  override def setSelectedFigure(playerNumber: Int, figureNumber: Int): GameBoard =
    copy(selectedFigure = Option(playerNumber, figureNumber))

  override def setStateNumber(stateNumber: Int): GameBoard = copy(stateNumber = Option(stateNumber))

  override def setStatementStatus(statement: Statements): GameBoard = copy(statementStatus = Option(statement))

  override def setPossibleCell(possibleCells: Set[Int]): GameBoard = copy(possibleCells = possibleCells)

  override def rollDice(): GameBoard = copy(dicedNumber = Dice().rollDice)

  override def setDicedNumber(dicedNumber: Option[Int]): GameBoard = copy(dicedNumber = dicedNumber)

  override def clearPossibleCells: GameBoard = copy(possibleCells = Set.empty)

  override def getPossibleCells(startCell: Int, diceNumber: Int): GameBoard = {

    var found: Set[Int] = Set[Int]()
    var needed: Set[Int] = Set[Int]()

    def recurse(currentCell: Int, diceNumber: Int): Unit = {
      if (diceNumber == 0 || cellList(currentCell).hasWall && diceNumber == 0)
        needed += currentCell
      if (diceNumber != 0 && cellList(currentCell).hasWall)
        return
      found += currentCell
      gameBoardGraph(currentCell).foreach(x => if (!found.contains(x) && diceNumber != 0) recurse(x, diceNumber - 1))
    }
    recurse(startCell, diceNumber)
    copy(possibleCells = needed)
  }

  override def setPossibleCellsTrueOrFalse(cellNumbers: List[Int], state: String): GameBoard = {
    state match {
      case "2" => copy(setPossibleCells(cellList.length - 1, cellNumbers, cellList)(markCell(boolean = true)))
      case _ => copy(setPossibleCells(cellList.length - 1, cellNumbers, cellList)(markCell(boolean = false)))
    }
  }

  override def setPossibleCells(cellListLength: Int, listOfCellNumbers: List[Int], listOfCells: List[Cell])(markCells: Int => Cell): List[Cell] =
    if (cellListLength == -1)
      listOfCells
    else if (listOfCellNumbers contains listOfCells(cellListLength).cellNumber)
      setPossibleCells(cellListLength - 1, listOfCellNumbers, listOfCells.updated(listOfCells(cellListLength).cellNumber, markCells(cellListLength)))(markCells)
    else
      setPossibleCells(cellListLength - 1, listOfCellNumbers, listOfCells)(markCells)

  override def markCell(boolean: Boolean)(cellNumber: Int): Cell = cellList(cellNumber).copy(possibleCells = boolean)


  override def removeActualPlayerAndFigureFromCell(playerNumber: Int, figureNumber: Int): GameBoard = {
    val cell = getPlayerFigure(playerNumber, figureNumber)
    copy(cellList.updated(cell, removePlayerFigureOnCell(cell)))
    copy(cellList.updated(cell, removePlayerOnCell(cell)))
  }

  override def removePlayerFigureOnCell(cellNumber: Int): Cell = cellList(cellNumber).copy(figureNumber = 0)

  override def removePlayerOnCell(cellNumber: Int): Cell = cellList(cellNumber).copy(playerNumber = 0)

  override def setWall(cellNumber: Int): GameBoard = copy(updateListWall(cellNumber))

  override def updateListWall(cellNumber: Int): List[Cell] =
    if (cellNumber >= 42 && !cellList(cellNumber).hasWall)
      cellList.updated(cellNumber, placeWall(cellNumber))
    else
      cellList

  override def placeWall(cellNumber: Int): Cell = cellList(cellNumber).copy(hasWall = true)

  override def removeWall(cellNumber: Int): GameBoard = copy(removeListWall(cellNumber))

  override def removeListWall(cellNumber: Int): List[Cell] = cellList.updated(cellNumber, setHasWallFalse(cellNumber))

  override def setHasWallFalse(cellNumber: Int): Cell = cellList(cellNumber).copy(hasWall = false)

  override def createPlayer(text: String): GameBoard = copy(players = players :+ Some(Player(players.length + 1, text)))

  override def nextPlayer(playerList: List[Option[Player]], playerNumber: Int): Option[Player] =
    if (playerNumber == playerList.length - 1)
        playerList.head
    else
        playerList(playerNumber + 1)

  override def setFigure(figureNumber: Int, cellNumber: Int): GameBoard = {
    copy(cellList.updated(cellNumber, setPlayerFigureOnCell(figureNumber, cellNumber)))
  }

  override def setPlayerFigureOnCell(figureNumber: Int, cellNumber: Int): Cell =
    cellList(cellNumber).copy(figureNumber = figureNumber)

  override def getPlayerFigure(playerNumber: Int, figureNumber: Int): Int = {
    val fieldNumber = cellList.filter(cell => cell.playerNumber == playerNumber && cell.figureNumber == figureNumber)
    val field = fieldNumber.head.cellNumber
    field
  }

  override def getHomeNr(playerNumber: Int, figureNumber: Int): Int =
    if (playerNumber == 1 && figureNumber == 1)
      0
    else
      (playerNumber - 1) * 5 + figureNumber - 1

  override def setPlayer(playerNumber: Int, cellNumber: Int): GameBoard =
    copy(cellList.updated(cellNumber, setPlayerOnCell(playerNumber, cellNumber)))

  override def setPlayerOnCell(playerNumber: Int, cellNumber: Int): Cell =
    cellList(cellNumber).copy(playerNumber = playerNumber)

  override def setPossibleFiguresTrueOrFalse(cellNumber: Int, stateNr: String): GameBoard = {
    stateNr match {
      case "1" => copy(setPossibleFigures(cellList.length - 1, cellNumber, cellList)(markFigure(boolean = true)))
      case _ => copy(setPossibleFigures(cellList.length - 1, cellNumber, cellList)(markFigure(boolean = false)))
    }
  }

  override def setPossibleFigures(cellListLength: Int, cellNumber: Int, cellList: List[Cell])(markFigures: Int => Cell): List[Cell] =
    if (cellListLength == -1)
      cellList
    else if (cellList(cellListLength).playerNumber == cellNumber)
      setPossibleFigures(
        cellListLength - 1,
        cellNumber,
        cellList.updated(cellList(cellListLength).cellNumber, markFigures(cellListLength))
      )(markFigures)
    else
      setPossibleFigures(cellListLength - 1, cellNumber, cellList)(markFigures)

  override def markFigure(boolean: Boolean)(cellNumber: Int): Cell = cellList(cellNumber).copy(possibleFigures = boolean)

}
