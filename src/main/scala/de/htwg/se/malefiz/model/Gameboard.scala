package de.htwg.se.malefiz.model

case class Gameboard() {

  /*
  Einzelne Spielfeldkomponenten
   */
  val playerbrackets = "( )"
  val brackets = "[ ]"
  val horizontal = "-"
  val vertical = "|"
  val gap = " "

  /*
  Grundbau für die einzelnen Spielfeldreihen
   */
  def levelVertical(distanceLeft: Int, numberPlayers: Int, blank: Int): String = gap * distanceLeft + vertical + (gap * blank + vertical) * numberPlayers

  def levelBracket1(distanceLeft: Int, numberPlayers: Int): String = gap * distanceLeft + brackets + (horizontal + brackets) * numberPlayers

  def levelBracket2(distanceLeft: Int, numberPlayers: Int, blank: Int): String = gap * distanceLeft + brackets + (gap * blank + brackets) * numberPlayers

  /*
  Grundbau für die Startpositionen
   */
  //def playerNumber(n: Int): String =

  def basisPlayer1: String = playerbrackets + (horizontal + playerbrackets) * 2

  def levelPlayer1(n: Int): String = gap * 4 + basisPlayer1 + (gap * 5 + basisPlayer1) * n

  def levelVerticalPlayer: String = vertical + gap * 7 + vertical

  def levelPlayer2(n: Int): String = gap * 5 + levelVerticalPlayer + (gap * 7 + levelVerticalPlayer) * (n - 1)

  def basisPlayer3: String = playerbrackets + horizontal + vertical + horizontal + playerbrackets

  def levelPlayer3(n: Int): String = gap * 5 + basisPlayer3 + (gap * 7 + basisPlayer3) * (n - 1)

  /*
  Aufbau der Startpositionen
   */
  def buildPlayer(n: Int): String = {
    f"""|${levelVertical(9, n - 1, 15)}
        |${levelPlayer3(n)}
        |${levelPlayer2(n)}
        |${levelPlayer1(n - 1)}""".stripMargin
  }

  /*
  Aufbau des Bodens
   */
  def bottom(n: Int): String = {
    f"""|${levelBracket1(0, n * 4)}
        |${levelVertical(1, n, 15)}
        |${levelBracket2(0, n, 13)}
        |${levelVertical(1, n, 15)}
        |${levelBracket1(0, n * 4)}
        |""".stripMargin
  }

  /*
  Aufbau der Pyramide
   */
  def buildBody(n: Int): String = {
    val m = n
    buildBody2(n, 9, 8, m)
  }

  def buildBody2(n: Int, vert: Int, brack: Int, m: Int): String = {
    if (n == 1 || n == 0) {
      buildTop(m)
    } else {
      buildBody2(n - 1, vert + 8, brack + 8, m) +
        f"""|${levelBracket1(brack, (n * 4) - 4)}
            |${levelVertical(vert, n - 1, 15)}
            |${levelBracket2(brack, n - 1, 13)}
            |${levelVertical(vert, n - 1, 15)}
            |""".stripMargin
    }
  }

  /*
  Aufbau des Daches
   */
  def buildTop(n: Int): String = {
    val m = n - 3
    f"""|${levelBracket1(0, n * 4)}
        |${levelVertical(1, 1, n * 16 - 1)}
        |${levelBracket2(0, 1, n * 15 + m)}
        |${levelVertical(1, 1, n * 16 - 1)}
        |${levelBracket1(0, n * 4)}
        |${levelVertical(n * 8 + 1, 0, 15)}
        |${levelBracket2(n * 8, 0, 13)}
        |${levelVertical(n * 8 + 1, 0, 15)}
        |""".stripMargin
  }

  /*
  Aufbau des Zielfeldes
   */
  def buildAim(n: Int): String = {
    f"""|${levelBracket1(n * 8, 0)}
        |${levelVertical(n * 8 + 1, 0, 15)}
        |""".stripMargin
  }

  /*
  Aufruf aller Baufunktionen : Stringrückgabe des ganzen Spielfeldes
   */
  def buildField(n: Int): String = {
    buildAim(n) + buildBody(n) + bottom(n) + buildPlayer(n)
  }

}
