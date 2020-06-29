package de.htwg.se.malefiz.controller
import scala.swing.Button
import scala.swing.event.Event

  class GameBoardChanged extends Event
  case class ButtonClicked(source: Button) extends Event
  // class CandidatesChanged extends Event