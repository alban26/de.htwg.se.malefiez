package de.htwg.se.malefiz.fileIoModule.model

trait FileIOInterface {

  def load: Unit

  def save(gameBoard: String, suffix: String): Unit

}
