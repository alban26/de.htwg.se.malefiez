package de.htwg.se.malefiz.model.playerComponent

import com.google.inject.Inject


case class Player @Inject() (playerNumber: Int, name: String) {

   override def toString:String =  name
}
