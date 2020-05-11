package de.htwg.se.malefiz.model

case class Gameboard() {




    val brackets = "[ ]"
    val horizontal = "-"
    val vertical = "|"
    val gap = " "


    def bracketsUebergang(k: Int, m: Int, n: Int): String = s"${gap*k}${brackets}${(gap*n+brackets)*m}"

    def stufeVerticals(k: Int, m: Int, n: Int): String = s"${gap*k}${vertical}${(gap*n+vertical)*m}"

    def stufeBrackets(k: Int, n: Int): String = s"${gap*k}${brackets}${(horizontal + brackets)*n}"

    def bottom(n: Int): String = {
      f"""|${stufeBrackets(0,n*4)}
          |${stufeVerticals(1, n, 15)}
          |${bracketsUebergang(0,n,13)}
          |${stufeVerticals(1,n,15)}
          |${stufeBrackets(0,n*4)}
          |""".stripMargin
    }

    def aufbauMitteHilf(n: Int, vert: Int, brack: Int): String = {
      if (n == 1) {
        aufbauDach(4)
      } else {
        aufbauMitteHilf(n - 1, vert + 8, brack + 8) +
          f"""|${stufeBrackets(brack, (n * 4) - 4)}
              |${stufeVerticals(vert, n - 1, 15)}
              |${bracketsUebergang(brack, n - 1, 13)}
              |${stufeVerticals(vert, n - 1, 15)}
              |""".stripMargin
      }
    }

    def aufbauMitte(n: Int): String = {
      aufbauMitteHilf(n,9,8)
    }

    def aufbauDach(n: Int): String = {
      f"""|${stufeBrackets(0,n*4)}
          |${stufeVerticals(1, 1, n*16-1)}
          |${bracketsUebergang(0, 1, n*15+1)}
          |${stufeVerticals(1, 1, n*16-1)}
          |${stufeBrackets(0,n*4)}
          |${stufeVerticals(n*8+1, 0, 15)}
          |${bracketsUebergang(n*8, 0, 13)}
          |${stufeVerticals(n*8+1, 0, 15)}
          |""".stripMargin
    }


    def aufbauZiel(n: Int): String = {
      f"""|${stufeBrackets(n*8,0)}
          |${stufeVerticals(n*8+1,0,15)}
          |""".stripMargin
    }

    def aufbauFeld(n: Int): String = {
      aufbauZiel(n) + aufbauMitte(n) + bottom(n)
    }



}
