package de.htwg.se.malefiz.controller

import de.htwg.se.malefiz.model.{Cell, Cube, GameBoard, Creator, Player}
import de.htwg.se.malefiz.util.{Observable, Observer}

import scala.collection.immutable.Queue
import scala.io.StdIn.readLine

class Controller(var gameBoard: GameBoard) extends Observable {


  def rollCube: Int = {
    Cube().getRandomNumber
  }

  def createPlayers(numberOfPlayers: Int): Unit = {
    gameBoard = gameBoard.createPlayerArray(numberOfPlayers)
  }

  def setPlayerFigure(playerNumber: Int, playerFigure: Int, cellNumber: Int) : Unit = {
    gameBoard = gameBoard.setPlayerFigure(playerNumber, playerFigure, cellNumber, gameBoard.playerArray, gameBoard.cellList)
    notifyObservers
  }

  def showPlayer()  =  {
    gameBoard = gameBoard.showPlayerStats(gameBoard.playerArray)
    notifyObservers
  }

  def setWall(n: Int): Unit = {
    gameBoard = gameBoard.setWall(n, gameBoard.cellList)
    notifyObservers
  }

  def gameBoardToString: String = gameBoard.createGameBoard()
}
