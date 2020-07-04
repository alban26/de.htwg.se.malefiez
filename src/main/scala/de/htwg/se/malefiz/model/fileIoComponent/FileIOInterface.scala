package de.htwg.se.malefiz.model.fileIoComponent

import de.htwg.se.malefiz.model.gameBoardComponent.GameboardInterface

trait FileIOInterface {
  def load: GameboardInterface
  def save(gameboard: GameboardInterface): Unit
}
