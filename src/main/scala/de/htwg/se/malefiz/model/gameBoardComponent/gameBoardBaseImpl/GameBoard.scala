package de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl

import de.htwg.se.malefiz.Malefiz._
import de.htwg.se.malefiz.controller.controllerComponent.GameStates.GameState
import de.htwg.se.malefiz.controller.controllerComponent.Statements.{Statements, addPlayer}
import de.htwg.se.malefiz.model.gameBoardComponent.GameBoardInterface
import de.htwg.se.malefiz.model.playerComponent.Player

import scala.collection.mutable.Map

case class GameBoard(cellList: List[Cell],
                     players: List[Player],
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


  override def returnGameBoardAsString(): String = {
    val string = new StringBuilder("Malefiz-GameBoard\n\n")
    string.append("Players: ")
    players.map(player => string.append(player.toString + " / "))
    string.append("\n" + "Players turn: " + playersTurn.getOrElse("No registered players").toString + "\n")
    string.append("Dice: " + dicedNumber.getOrElse("Dice is not rolled yet").toString + "\n")
    string.append("Status: " + statementStatus.get.toString + "\n")
    string.toString()
  }

  override def setPlayersTurn(player: Option[Player]): GameBoard = copy(playersTurn = player)

  override def setSelectedFigure(playerNumber: Int, figureNumber: Int): GameBoard =
    copy(selectedFigure = Option(playerNumber, figureNumber))

  override def setStateNumber(stateNumber: Int): GameBoard = copy(stateNumber = Option(stateNumber))

  override def setStatementStatus(statement: Statements): GameBoard = copy(statementStatus = Option(statement))

  override def setPossibleCell(possibleCells: Set[Int]): GameBoard = copy(possibleCells = possibleCells)

  override def rollDice(): GameBoard = copy(dicedNumber = Dice().rollDice)

  override def setDicedNumber(dicedNumber: Int): GameBoard = copy(dicedNumber = Option(dicedNumber))

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


  override def setPosiesCellTrue(listOfCellNumbers: List[Int]): GameBoard = copy(setPossibleCell1True(cellList.length - 1, listOfCellNumbers, cellList))

  override def setPossibleCell1True(cellListLength: Int, listOfCellNumbers: List[Int], list: List[Cell]): List[Cell] =
    if (cellListLength == -1)
      list
    else if (listOfCellNumbers contains list(cellListLength).cellNumber)
      setPossibleCell1True(cellListLength - 1, listOfCellNumbers, list.updated(list(cellListLength).cellNumber, setPossibleCellTrue(cellListLength)))
    else
      setPossibleCell1True(cellListLength - 1, listOfCellNumbers, list)


  override def setPossibleCellTrue(cellNumber: Int): Cell = cellList(cellNumber).copy(possibleCells = true)

  override def setPosiesCellFalse(listOfCellNumbers: List[Int]): GameBoard =
    copy(setPossibleCell1False(cellList.length - 1, listOfCellNumbers, cellList))

  override def setPossibleCell1False(cellListLength: Int, listOfCells: List[Int], list: List[Cell]): List[Cell] =
    if (cellListLength == -1)
      list
    else if (listOfCells contains list(cellListLength).cellNumber)
      setPossibleCell1False(cellListLength - 1, listOfCells, list.updated(list(cellListLength).cellNumber, setPossibleCellFalse(cellListLength)))
    else
      setPossibleCell1False(cellListLength - 1, listOfCells, list)

  override def setPossibleCellFalse(cellNumber: Int): Cell = cellList(cellNumber).copy(possibleCells = false)

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

  override def createPlayer(text: String): GameBoard = copy(players = players :+ Player(players.length + 1, text))

  override def nextPlayer(playerList: List[Player], playerNumber: Int): Option[Player] =
    if (playerNumber == playerList.length - 1)
      Option(playerList.head)
    else
      Option(playerList(playerNumber + 1))

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

  override def setPosiesTrueOrFalse(cellNumber: Int, state: GameState): GameBoard = {
    state.state.toString match {
      case "1" => copy(setPossibleFigures(cellList.length - 1, cellNumber, cellList)(markFigure(true)))
      case _ => copy(setPossibleFigures(cellList.length - 1, cellNumber, cellList)(markFigure(false)))
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
