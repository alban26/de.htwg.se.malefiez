package de.htwg.se.malefiz.model.gameBoardComponent.gameBoardBaseImpl

import de.htwg.se.malefiz.model.gameBoardComponent.CubeInterface

case class Cube() extends CubeInterface {

  def getRandomNumber : Int = {
    val randomNumber = scala.util.Random.nextInt(6) + 1
    randomNumber
  }

}
