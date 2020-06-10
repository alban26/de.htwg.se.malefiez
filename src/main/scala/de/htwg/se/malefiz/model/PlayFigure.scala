package de.htwg.se.malefiz.model

import java.awt.Color

case class PlayFigure(n: Int, player: Player, color: Color){


  private val colori = List(Console.BLUE, Console.GREEN, Console.RED, Console.YELLOW)

  val t = if(color == Color.BLUE) 0 else if (color == Color.GREEN) 1 else if(color == Color.RED) 2 else 3

  override def toString: String = player+""//.charAt(0) +  colori(t)
}



