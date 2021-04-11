package de.htwg.se.malefiz.playerModule.model.playerServiceComponent

case class Dice() {

  def rollDice: Option[Int] = {
    val randomNumber = Option(scala.util.Random.nextInt(6) + 1)
    randomNumber
  }
}
