package de.htwg.se.malefiz

import org.scalatest._
import org.scalatest.matchers.should.Matchers

class MalefizSpec extends WordSpec with Matchers {

  "The Malefiz main class" should {
    val a = Malefiz
    "accept text input as argument without readline loop, to test it from command line " in {
      a.tui.processInput1("n Robert")
      a.tui.processInput1("n Alban")
      a.tui.processInput1("start")
      a.tui.processInput1("end")
    }
  }
}


