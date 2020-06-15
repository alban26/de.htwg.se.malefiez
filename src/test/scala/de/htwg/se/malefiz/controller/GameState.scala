package de.htwg.se.malefiz.controller

object GameState extends Enumeration{
  type GameState = Value
  val IDLE, ROLL, SELECT_PLAYER, SET_PLAYER, SET_WALL, WON = Value

  val map = Map[GameState, String](
    IDLE -> "",
    ROLL ->"hat gewürfelt",
    SELECT_PLAYER -> "hat folgende Figur ausgewählt",
    SET_PLAYER -> "hat Figur gesetzt",
    SET_WALL -> "hat eine Mauer gesetzt",
    WON -> "hat gewonnen!"
  )

  def message(gameStatus: GameState) = {
    map(gameStatus)
  }

}