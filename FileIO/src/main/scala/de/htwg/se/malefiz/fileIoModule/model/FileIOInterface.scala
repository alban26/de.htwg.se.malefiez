package de.htwg.se.malefiz.fileIoModule.model

trait FileIOInterface {

  def load: String

  def save(gameBoard: String): Unit

}
