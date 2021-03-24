package de.htwg.se.malefiz.model.fileIoComponent.fileIoXmlImpl

import com.google.inject.{Guice, Inject}
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.malefiz.MalefizModule
import de.htwg.se.malefiz.controller.controllerComponent.ControllerInterface
import de.htwg.se.malefiz.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.malefiz.model.fileIoComponent.FileIOInterface
import de.htwg.se.malefiz.model.gameBoardComponent.GameBoardInterface
import de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl.Cell
import de.htwg.se.malefiz.model.playerComponent.Player

import scala.xml.{Elem, PrettyPrinter}

class FileIO @Inject() extends FileIOInterface {

  override def loadController: ControllerInterface = {
    val file = scala.xml.XML.loadFile("gameboardList.xml")

    val gameStateNodes = file \\ "gameState"
    val controllerNeu = new Controller(load)

    val dice = (file \\ "dicedNumber" \ "@number").text.toInt
    controllerNeu.setDicedNumber(dice)

    val playerZahl = (file \\ "playersTurn" \ "@turnZ").text.toInt
    //val playerName = (file \\ "playersTurn" \ "@turnN").text
    val stateNumber = (file \\ "gameState" \ "@state").text.toInt
    val selectedFigure_1 = (file \\ "selectedFigure" \ "@sPlayer").text.toInt
    val selectedFigure_2 = (file \\ "selectedFigure" \ "@sFigure").text.toInt

    controllerNeu.setSelectedFigure(selectedFigure_1, selectedFigure_2)
    controllerNeu.setStateNumber(stateNumber.toInt)

    controllerNeu.setPlayersTurn(Option.apply(controllerNeu.getPlayer(playerZahl - 1)))
    controllerNeu
  }

  override def load: GameBoardInterface = {
    val injector = Guice.createInjector(new MalefizModule)
    var gameboard: GameBoardInterface = injector.instance[GameBoardInterface]
    //var gameStateT: GameState = injector.instance[GameState]
    //var controller: ControllerInterface = injector.instance[ControllerInterface]

    val file = scala.xml.XML.loadFile("gameboardList.xml")
    val cellNodes = file \\ "cell"
    val playerNodes = file \\ "player"
    val pCellNodes = file \\ "pCells"

    var found: Set[Int] = Set[Int]()
    for (pos <- pCellNodes) {
      val possCell = (pos \ "@posCell").text.toInt
      gameboard = gameboard.setPosiesCellTrue(List(possCell))
      found += possCell
    }
    gameboard = gameboard.setPossibleCell(found)

    for (player <- playerNodes) {
      val playerName: String = (player \ "@playername").text
      if (playerName != "")
        gameboard = gameboard.createPlayer(playerName)
    }

    for (cell <- cellNodes) {

      val cellNumber: Int = (cell \ "@cellnumber").text.toInt
      val playerNumber: Int = (cell \ "@playernumber").text.toInt
      val figureNumber: Int = (cell \ "@figurenumber").text.toInt
      val hasWall: Boolean = (cell \ "@haswall").text.toBoolean

      gameboard = gameboard.setPlayer(playerNumber, cellNumber)
      gameboard = gameboard.setFigure(figureNumber, cellNumber)

      if (hasWall)
        gameboard = gameboard.setWall(cellNumber)
      if (!hasWall)
        gameboard = gameboard.removeWall(cellNumber)

    }

    gameboard
  }

  override def save(gameboard: GameBoardInterface, controller: ControllerInterface): Unit =
    saveString(gameboard, controller)

  def saveString(gameboard: GameBoardInterface, controller: ControllerInterface): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("gameboardList.xml"))
    val prettyPrinter = new PrettyPrinter(80, 2)
    val xml = prettyPrinter.format(gameboardToXml(gameboard, controller))
    pw.write(xml)
    pw.close()
  }

  def gameboardToXml(gameboard: GameBoardInterface, controller: ControllerInterface): Elem =
    <gameboard size={gameboard.getPlayer.size.toString}>
      {

      for {
        l1 <- gameboard.getCellList.indices
      } yield cellToXml(l1, gameboard.getCellList(l1))
    }
      <player>
        {
      for {
        l1 <- gameboard.getPlayer.indices
      } yield playerToXml(l1, gameboard.getPlayer(l1))
    }
      </player>
      <possibleCells>
        {
      for {
        l1 <- gameboard.getPossibleCells
      } yield possibleCellsToXml(l1)
    }
      </possibleCells>
      <playersTurn turnZ={controller.getPlayersTurn.get.playerNumber.toString} turnN ={
      controller.getPlayersTurn.get.name
    }></playersTurn>

      <dicedNumber number={controller.getGameBoard.dicedNumber.toString}></dicedNumber>

      <selectedFigure sPlayer={controller.getSelectedFigure.get._1.toString} sFigure={
      controller.getSelectedFigure.get._2.toString
    } ></selectedFigure>

      <gameState state={controller.getGameState.state.toString}></gameState>

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
