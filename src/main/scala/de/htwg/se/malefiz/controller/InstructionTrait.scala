package de.htwg.se.malefiz.controller

trait InstructionTrait {
  type Handler0 = PartialFunction[Request,Request]
  type Handler1 = PartialFunction[Request,String]

  val log = (message: String) => {
    println(message)
    message
  }
}
