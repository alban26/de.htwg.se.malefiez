package de.htwg.se.malefiz.controller

import de.htwg.se.malefiz.model.{Cell, Cube, GameBoard, Creator, Player}
import de.htwg.se.malefiz.util.{Observable, Observer}


class Controller(var gameBoard: GameBoard) extends Observable {

  def rollCube: Int = {
    Cube().getRandomNumber
  }

  def createPlayers(numberOfPlayers: Int): Unit = {
    gameBoard = gameBoard.createPlayerArray(numberOfPlayers)
  }

  def getFigure(pn: Int, fn:Int) : Int = {
    val a = gameBoard.getPlayerFigure(pn, fn)
    a
  }

  def getSet(startCell: Int, cubeNumber: Int) : Set[Int] = {
    val possibleCells = gameBoard.getPossibleCells(startCell, cubeNumber)
    possibleCells
  }

  def setPlayerFigure(playerNumber: Int, playerFigure: Int, cellNumber: Int) : Unit = {
    gameBoard = gameBoard.removeActualPlayerAndFigureFromCell(playerNumber, playerFigure, cellNumber)
    gameBoard = gameBoard.setPlayer(playerNumber, cellNumber)
    gameBoard = gameBoard.setFigure(playerFigure, cellNumber)
    notifyObservers
  }

  def setWall(n: Int): Unit = {
    gameBoard = gameBoard.setWall(n)
    notifyObservers
  }

  def gameBoardToString: String = gameBoard.createGameBoard()
}
