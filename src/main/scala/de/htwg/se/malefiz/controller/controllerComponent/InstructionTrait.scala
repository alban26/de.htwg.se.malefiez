package de.htwg.se.malefiz.controller.controllerComponent

import de.htwg.se.malefiz.controller.controllerComponent.Statements.Statements

trait InstructionTrait {
  type Handler0 = PartialFunction[Request,Request]
  type Handler1 = PartialFunction[Request,String]
  type Handler2 = PartialFunction[StatementRequest,String]

  val log = (message: String) => {
    println(message)
    message
  }
}
