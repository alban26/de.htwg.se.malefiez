import java.awt.Color

import de.htwg.se.malefiz.model.{PlayFigure, Player}

def createPlayer(n: Int): List[Player] = {
  if(n == 0){
    Nil
  } else {
    createPlayer(n-1) :+ Player("Spieler1",new PlayFigure(n,Color.RED))
  }
}

val l : List[Player] = createPlayer(3)


