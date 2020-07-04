package de.htwg.se.malefiz.model.fileIoComponent.fileIoJsonImpl

import de.htwg.se.malefiz.model.fileIoComponent.FileIOInterface
import de.htwg.se.malefiz.model.gameBoardComponent.GameboardInterface

class FileIO extends FileIOInterface{
  override def load: GameboardInterface = ???

  override def save(gameboard: GameboardInterface): Unit = ???
}
