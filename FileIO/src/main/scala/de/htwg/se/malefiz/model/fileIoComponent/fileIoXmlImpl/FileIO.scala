package de.htwg.se.malefiz.model.fileIoComponent.fileIoXmlImpl

import com.google.inject.{Guice, Inject}
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.malefiz.MalefizModule
import de.htwg.se.malefiz.controller.controllerComponent.ControllerInterface
import de.htwg.se.malefiz.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.malefiz.model.fileIoComponent.FileIOInterface
import de.htwg.se.malefiz.model.gameBoardComponent.GameBoardInterface
import de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl.Cell
import de.htwg.se.malefiz.playerModule.model.playerComponent.Player

import scala.xml.{Elem, PrettyPrinter}

class FileIO @Inject() extends FileIOInterface {

  override def loadController: ControllerInterface = {
    val file = scala.xml.XML.loadFile("gameboardList.xml")

    val gameStateNodes = file \\ "gameState"
    val newController = new Controller(load)

    val dice = (file \\ "dicedNumber" \ "@number").text.toInt
    newController.gameBoard.rollDice()

    val playerZahl = (file \\ "playersTurn" \ "@turnZ").text.toInt
    //val playerName = (file \\ "playersTurn" \ "@turnN").text
    val stateNumber = (file \\ "gameState" \ "@state").text.toInt
    val selectedFigure_1 = (file \\ "selectedFigure" \ "@sPlayer").text.toInt
    val selectedFigure_2 = (file \\ "selectedFigure" \ "@sFigure").text.toInt

    newController.setSelectedFigure(selectedFigure_1, selectedFigure_2)
    newController.setStateNumber(stateNumber.toInt)

    newController.setPlayersTurn(newController.gameBoard.players(playerZahl - 1))
    newController
  }

  override def load: GameBoardInterface = {
    val injector = Guice.createInjector(new MalefizModule)
    var gameBoard: GameBoardInterface = injector.instance[GameBoardInterface]
    //var gameStateT: GameState = injector.instance[GameState]
    //var controller: ControllerInterface = injector.instance[ControllerInterface]

    val file = scala.xml.XML.loadFile("gameboardList.xml")
    val cellNodes = file \\ "cell"
    val playerNodes = file \\ "player"
    val pCellNodes = file \\ "pCells"

    var found: Set[Int] = Set[Int]()
    for (pos <- pCellNodes) {
      val possCell = (pos \ "@posCell").text.toInt
      gameBoard = gameBoard.setPossibleCellsTrueOrFalse(List(possCell), gameBoard.stateNumber.toString)
      found += possCell
    }
    gameBoard = gameBoard.setPossibleCell(found)

    for (player <- playerNodes) {
      val playerName: String = (player \ "@playername").text
      if (playerName != "")
        gameBoard = gameBoard.createPlayer(playerName)
    }

    for (cell <- cellNodes) {

      val cellNumber: Int = (cell \ "@cellnumber").text.toInt
      val playerNumber: Int = (cell \ "@playernumber").text.toInt
      val figureNumber: Int = (cell \ "@figurenumber").text.toInt
      val hasWall: Boolean = (cell \ "@haswall").text.toBoolean

      gameBoard = gameBoard.setPlayer(playerNumber, cellNumber)
      gameBoard = gameBoard.setFigure(figureNumber, cellNumber)

      if (hasWall)
        gameBoard = gameBoard.setWall(cellNumber)
      if (!hasWall)
        gameBoard = gameBoard.removeWall(cellNumber)

    }

    gameBoard
  }

  override def save(gameboard: GameBoardInterface, controller: ControllerInterface): Unit =
    saveString(gameboard, controller)

  def saveString(gameboard: GameBoardInterface, controller: ControllerInterface): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("gameboardList.xml"))
    val prettyPrinter = new PrettyPrinter(80, 2)
    val xml = prettyPrinter.format(gameBoardToXml(gameboard, controller))
    pw.write(xml)
    pw.close()
  }

  def gameBoardToXml(gameBoard: GameBoardInterface, controller: ControllerInterface): Elem =
    <gameboard size={gameBoard.players.size.toString}>
      {

      for {
        l1 <- gameBoard.cellList.indices
      } yield cellToXml(l1, gameBoard.cellList(l1))
    }
      <player>
        {
      for {
        l1 <- gameBoard.players.indices
      } yield playerToXml(l1, gameBoard.players(l1).get)
    }
      </player>
      <possibleCells>
        {
      for {
        l1 <- gameBoard.possibleCells
      } yield possibleCellsToXml(l1)
    }
      </possibleCells>
      <playersTurn turnZ={controller.gameBoard.playersTurn.get.playerNumber.toString} turnN ={
      controller.gameBoard.playersTurn.get.name
    }></playersTurn>

      <dicedNumber number={controller.gameBoard.dicedNumber.toString}></dicedNumber>

      <selectedFigure sPlayer={controller.gameBoard.selectedFigure.get._1.toString} sFigure={
      controller.gameBoard.selectedFigure.get._2.toString
    } ></selectedFigure>

      <gameState state={controller.getGameState.currentState.toString}></gameState>

    </gameboard>

  def possibleCellsToXml(l1: Int): Elem =
    <pCells posCell={l1.toString}>
      {l1}
    </pCells>
  /*
 def gameStateToXml(state: GameState): Elem = {
   <gameState state={state.state.toString}>
     {state}
   </gameState>
 }


 dplayersTurnToXml(player: Player): Elem = {
   <playersTurn playersturnname ={player.name}>
     {player}
   </playersTurn>
 }
   */
  def playerToXml(i: Int, player: Player): Elem =
    <player playernumber={player.playerNumber.toString} playername={player.name}>
 {player}
 </player>

  def cellToXml(l1: Int, cell: Cell): Elem =
    <cell cellnumber={cell.cellNumber.toString} playernumber={cell.playerNumber.toString}
         figurenumber={cell.figureNumber.toString} haswall={cell.hasWall.toString}>
     {cell}
   </cell>
}
