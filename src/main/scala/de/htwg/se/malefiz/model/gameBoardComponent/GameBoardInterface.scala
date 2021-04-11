package de.htwg.se.malefiz.model.gameBoardComponent

import de.htwg.se.malefiz.controller.controllerComponent.GameStates.GameState
import de.htwg.se.malefiz.controller.controllerComponent.Statements.Statements
import de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl.Cell
import de.htwg.se.malefiz.model.playerComponent.Player

import scala.collection.mutable.Map
import scala.io.BufferedSource
import scala.util.Try


trait DiceInterface {

  def rollDice: Option[Int]

}

trait CreatorInterface {

  def readTextFile(filename: String): Try[Option[BufferedSource]]

  def getCellList(inputFile: String): List[Cell]

  def getCellGraph(fileInput: String): Map[Int, Set[Int]]

  def updateCellGraph(key: Int, value: Int, map: Map[Int, Set[Int]]): Map[Int, Set[Int]]

}
