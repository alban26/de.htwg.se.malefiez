package de.htwg.se.malefiz.model

case class Cube() {

  def getRandomNumber : Int = {
    val randomNumber = scala.util.Random.nextInt(6) + 1
    randomNumber
  }

}

