package de.htwg.se.malefiz

import de.htwg.se.malefiz.aview.Tui
import de.htwg.se.malefiz.model.{Cube, GameBoard, Player}

import scala.io.StdIn

object Malefiz {


  val tui = new Tui

  def main(args: Array[String]): Unit = {


    println("Willkommen bei Malefiz!")

    var input: String = ""

    do {
      input = io.StdIn.readLine()


    } while(input != "end")
    }

}
