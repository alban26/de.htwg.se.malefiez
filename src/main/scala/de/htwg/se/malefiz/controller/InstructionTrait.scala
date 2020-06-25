package de.htwg.se.malefiz.controller

trait InstructionTrait {
  type Handler0 = PartialFunction[Option[Request],Option[Request]]
  type Handler1 = PartialFunction[Option[Request],String]

  val log = (message: String) => {
    println(message)
    message
  }
}
