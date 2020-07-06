package de.htwg.se.malefiz.model.fileIoComponent.fileIoXmlImpl

import com.google.inject.Guice
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.malefiz.MalefizModule
import de.htwg.se.malefiz.model.fileIoComponent.FileIOInterface
import de.htwg.se.malefiz.model.gameBoardComponent.GameboardInterface
import de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl.Cell
import de.htwg.se.malefiz.model.playerComponent.Player

import scala.xml.{Elem, NodeBuffer, PrettyPrinter}
class FileIO extends FileIOInterface{
  override def load: GameboardInterface = {
    val injector = Guice.createInjector(new MalefizModule)
    var gameboard: GameboardInterface = injector.instance[GameboardInterface]
    val file = scala.xml.XML.loadFile("gameboardList.xml")

    val cellNodes = (file \\ "cell")

    val playerSize = (file \\ "gameboard" \ "@size")
    val psize = playerSize.text.toInt

    for (cell <- cellNodes.slice(0,psize)){
      val playerName: String = (cell \ "@playername").text
      gameboard = gameboard.createPlayer(playerName)
    }

    for(cell <- cellNodes) {

      val cellNumber: Int = (cell \ "@cellnumber").text.toInt
      val playerNumber: Int = (cell \ "@playernumber").text.toInt
      val figureNumber: Int = (cell \ "@figurenumber").text.toInt
      val hasWall: Boolean = (cell \ "@haswall").text.toBoolean


      gameboard = gameboard.setPlayer(playerNumber,cellNumber)
      gameboard = gameboard.setFigure(figureNumber,cellNumber)
      if(hasWall){
        gameboard = gameboard.setWall(cellNumber)
      }
      if(!hasWall){
        gameboard = gameboard.rWall(cellNumber)
      }

    }
    gameboard
  }

  override def save(gameboard: GameboardInterface): Unit = {
    saveString(gameboard)
  }

  def saveString(gameboard: GameboardInterface): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("gameboardList.xml"))
    val prettyPrinter = new PrettyPrinter(1,1)
    val xml = prettyPrinter.format(gameboardToXml(gameboard))
    pw.write(xml)
    pw.close()
  }

  def gameboardToXml(gameboard: GameboardInterface) : Elem= {
    <gameboard size={gameboard.getPlayer.size.toString}>
      {
        for {
          l1 <- gameboard.getCellList.indices
          l2 <- gameboard.getPlayer.indices
        } yield cellToXml(l1,gameboard.getCellList,l2,gameboard.getPlayer)
      }
    </gameboard>
  }



  def cellToXml(l1: Int, cell: List[Cell], l2: Int, player: List[Player]): Elem = {
    <cell cellnumber={cell(l1).cellNumber.toString} playernumber={cell(l1).playerNumber.toString}
          figurenumber={cell(l1).figureNumber.toString} haswall={cell(l1).hasWall.toString} playername={player(l2).name} playersize={player.size.toString}>
      {cell}
    </cell>
  }
}
