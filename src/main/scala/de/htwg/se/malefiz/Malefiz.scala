package de.htwg.se.malefiz

import com.google.inject.{Guice, Injector}
import de.htwg.se.malefiz.aview.Tui
import de.htwg.se.malefiz.aview.gui.{EntryGui, SwingGui}
import de.htwg.se.malefiz.controller.controllerComponent.{ControllerInterface, GameBoardChanged}

import scala.io.StdIn.readLine

object Malefiz {

  val cellConfigFile = "project/mainCellConfiguration"
  val cellLinksFile = "project/mainCellLinks"

  val injector: Injector = Guice.createInjector(new MalefizModule)
  val controller: ControllerInterface = injector.getInstance(classOf[ControllerInterface])
  val tui = new Tui(controller)

  var entryGui = new EntryGui(controller)
  var gui = new SwingGui(controller)
  controller.publish(new GameBoardChanged)
  def main(args: Array[String]): Unit = {
    var input: String = ""
    do {
      input = readLine()
      tui.processInput1(input)
    } while (input != "end")
  }

}
