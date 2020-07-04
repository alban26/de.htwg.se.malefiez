package de.htwg.se.malefiz.model.fileIoComponent.fileIoXmlImpl

import de.htwg.se.malefiz.model.fileIoComponent.FileIOInterface
import de.htwg.se.malefiz.model.gameBoardComponent.GameboardInterface

import scala.xml.{Elem, PrettyPrinter}
class FileIO extends FileIOInterface{
  override def load: GameboardInterface = {
    var gamebard: GameboardInterface = null
    val file = scala.xml.XML.loadFile("gameboardList.xml")
    val sizeAttr = (file \\ "gameboard" \ "@size")
    val size = sizeAttr.text.toInt
    val gameboardObject = (file \\ "gameboard")
    println(sizeAttr)
    println(gameboardObject)
    null
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
    pw.close
  }

  def gameboardToXml(gameboard: GameboardInterface) : Elem= {
    <gameboard size={gameboard.getCellList.size.toString}>

      {gameboard.getCellList}

    </gameboard>
  }
/*
  def cellToXml(gameboard: GameboardInterface): Elem = {
    <cell>
      {gameboard}
    </cell>
  }*/
}
