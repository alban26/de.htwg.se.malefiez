package de.htwg.se.malefiz.model

case class PlayFigure(figureNumber: Int,  inCell: Cell) {

  override def toString:String = "Figur " + figureNumber + " steht auf Feld: " + inCell.cellNumber

}
