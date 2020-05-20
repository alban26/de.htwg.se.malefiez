package de.htwg.se.malefiz.model

case class GenFields() {

  def genCells(n: Int): List[Field] = {
    if(n == 0){
      Nil
    } else {
      genCells(n-1) :+ Field(n-1,0,true,true,null,true)
    }
  }

  def setWall(list: List[Field]): List[Field] = {


  }

  def setDestination(list: List[Field]): List[Field] = {

  }

  def setWallPermission(list: List[Field],start: Int, stop: Int): List[Field] = {
    //index-slicing
  }



}
