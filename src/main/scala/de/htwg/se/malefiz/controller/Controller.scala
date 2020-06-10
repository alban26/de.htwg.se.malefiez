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
/*
  def setPlayerFigure(playerNumber: Int, playerFigure: Int, cellNumber: Int) : Unit = {
    gameBoard = gameBoard.setPlayerFigure(playerNumber, playerFigure, cellNumber, gameBoard.players, gameBoard.cellList)
    notifyObservers
  }*/

  def showPlayer(): Unit =  {
    gameBoard = gameBoard.showPlayerStats(gameBoard.players)
    notifyObservers
  }

  def setWall(n: Int): Unit = {
    gameBoard = gameBoard.setWall(n)
    notifyObservers
  }

  def gameBoardToString: String = gameBoard.createGameBoard()
}
