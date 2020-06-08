package de.htwg.se.malefiz

import de.htwg.se.malefiz.aview.Tui
import de.htwg.se.malefiz.controller.Controller

import de.htwg.se.malefiz.model.{Cube, GameBoard, SetupState, ListCreator, PlayFigure, Player, SetupState}

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
        tui.setupTUI(input,0)
      case _ => None
    }

    }

}
