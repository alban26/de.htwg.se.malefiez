
case class Player(playerNumber: Int, name: String) {

  override def toString:String = "Playernumber: " + (playerNumber)
}

val lop:List[Player] = List(Player(1,"alban"),Player(1,"Robert"),Player(3,"wirbser"))

println(lop.lift(0))

def nextPlayer(list: List[Player], n: Int): Player = {
  if(n == list.length-1) {
    list.head
  } else {
    list(n+1)
  }
}




