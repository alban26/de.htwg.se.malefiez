package de.htwg.se.malefiz.gameBoardModule.util

trait Command {

    def doStep():Unit
    def undoStep():Unit
    def redoStep():Unit

}
