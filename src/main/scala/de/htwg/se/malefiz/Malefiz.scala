package de.htwg.se.malefiz

import de.htwg.se.malefiz.aview.Tui
import de.htwg.se.malefiz.controller.Controller

import de.htwg.se.malefiz.model.{Cube, GameBoard, SetupState, ListCreator, PlayFigure, Player}

import scala.io.StdIn
import scala.io.StdIn.readLine



object Malefiz {


  val cellList = ListCreator()
  val cellList1 = cellList.getCellList


  val controller = new Controller(new GameBoard(cellList1))
  val tui = new Tui(controller)



  def main(args: Array[String]): Unit = {



    println("Willkommen bei Malefiz!")


    var input: String = ""



    SetupState.values.foreach
    {
      // Matching values in Enumeration
      case d if ( d == SetupState.spielerAnzahl ) =>
        tui.setupTUI(0)
        input = readLine()
        tui.processInput(input)
      case s if(s == SetupState.spielerNamen) =>
        tui.setupTUI(1)
        input = readLine()
        tui.processInput(input)
      case _ => None
    }

    }
}
