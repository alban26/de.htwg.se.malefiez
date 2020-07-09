package de.htwg.se.malefiz.controller.controllerComponent

trait InstructionTrait {

  type Handler0 = PartialFunction[Request,Request]
  type Handler1 = PartialFunction[Request,String]
  type Handler2 = PartialFunction[StatementRequest,String]

  val log: String => String = (message: String) => {
    println(message)
    message
  }

}
