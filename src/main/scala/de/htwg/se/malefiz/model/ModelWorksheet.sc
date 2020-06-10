import java.awt.Color

import de.htwg.se.malefiz.Malefiz.cellList1
import de.htwg.se.malefiz.model.{Cell, GameBoard, ListCreator}



/*
val s = List(1,2,3,4)

def an (l: List[Int],n: Int): List[Int] = {
  if(n == 0)
    {
      l.updated(n,2)
    }else { println(l)
    an(l.updated(n,2),n-1)

  }
}

val t = an(s,3)

println(t)
*/

val cellList = ListCreator()
val cellList1 = cellList.getCellList

val t = List("Alban", "Robert","Wirbser","hallo")
val pl = GameBoard(cellList1)

val li = pl.createPlayer(t.length-1,t)

val s = pl.createFiguresH(li)

val lc = pl.createFigureCells2(s.length-1,s)



def updateListFigure(lis: List[Cell], n: Int, cl: List[Cell]): List[Cell] = {
    if(n == 0) {
        lis.updated(n,cl(n))
    } else {
        updateListFigure(lis.updated(n,cl(n)),n-1,cl)
    }

}

println(lc.length)
val x: List[Cell] = updateListFigure(cellList1,lc.length-1,lc)
println(cellList1)