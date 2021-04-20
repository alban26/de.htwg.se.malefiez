package de.htwg.se.malefiz

import com.google.inject.{Guice, Injector}
import de.htwg.se.malefiz.aview.Tui
import de.htwg.se.malefiz.aview.gui.{EntryGui, EntryPlayerGui}
import de.htwg.se.malefiz.controller.controllerComponent.ControllerInterface

import scala.io.StdIn.readLine

object Malefiz {

  val injector: Injector = Guice.createInjector(new MalefizModule)
  val controller: ControllerInterface = injector.getInstance(classOf[ControllerInterface])

  val tui = new Tui(controller)
  val entryGui = new EntryGui(controller)
  val entryPlayerGui = new EntryPlayerGui(controller)

  def main(args: Array[String]): Unit = {
    var input: String = ""
    do {
      input = readLine()
      tui.runInput(input)
    } while (input != "end")
  }

}
