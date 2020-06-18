package de.htwg.se.malefiz.controller

import de.htwg.se.malefiz.controller.GameState.{GameState, map}

object GameState extends Enumeration{
  type GameState = Value
  val IDLE, ENTRY_NAMES, PLAYERS_TURN,  WON = Value

  val map = Map[GameState, String](
    IDLE -> "",
    ENTRY_NAMES -> "Namen eingeben",
    PLAYERS_TURN -> "Spieler ist dran",
    WON -> "hat gewonnen!"
  )

  def message(gameStatus: GameState) = {
    map(gameStatus)
  }
}

object PlayingState extends Enumeration {
  type PlayingState = Value
  val ROLL, SELECT_PLAYER, SHOW_CELLS,SET_PLAYER, SET_WALL = Value

  val map = Map[PlayingState, String](
  ROLL ->"Drücke bliebige Taste um zu würfeln",
  SELECT_PLAYER -> "Wähle deine gewünschte Figur aus",
  SHOW_CELLS -> "Wähle dein Zielfeld aus",
  SET_PLAYER -> "Setze nun deinen Spieler",
  SET_WALL -> "hat eine Mauer gesetzt"
  )

  def message(playingState: PlayingState) = {
    map(playingState)
  }

}
