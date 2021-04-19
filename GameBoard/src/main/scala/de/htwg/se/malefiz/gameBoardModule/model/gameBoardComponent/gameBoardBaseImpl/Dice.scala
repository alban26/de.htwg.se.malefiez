package de.htwg.se.malefiz.gameBoardModule.model.gameBoardComponent.gameBoardBaseImpl

import de.htwg.se.malefiz.gameBoardModule.model.gameBoardComponent.DiceInterface

case class Dice() extends DiceInterface {

  def rollDice: Option[Int] = {
    val randomNumber = Option(scala.util.Random.nextInt(6) + 1)
    randomNumber
  }

}
