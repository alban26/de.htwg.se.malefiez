package de.htwg.se.malefiz.aview.gui

import de.htwg.se.malefiz.controller.Controller

import scala.swing.{Action, BorderPanel, Button, Dimension, Frame, GridPanel, Menu, MenuBar, MenuItem, Orientation, SplitPane}
import BorderPanel.Position._
import scala.swing.event.ButtonClicked

class EntryGui(controller: Controller) extends Frame {

  title = "Wilkommen zu Malefiz"

  val newGameButton = new Button("New Game")
  val quitButton = new Button("Quit")

  menuBar = new MenuBar{
    contents += new Menu("Malefiz") {
      contents += new MenuItem(Action("Quit") {
        System.exit(0)
      })
    }
  }

  contents = new GridPanel(2, 1) {
    contents += new BorderPanel {
      layout += newGameButton -> Center
      layout += quitButton -> South
    }
  }


 listenTo(newGameButton, quitButton)

  reactions += {
    case ButtonClicked(`quitButton`) => System.exit(0)
    case ButtonClicked(`newGameButton`) =>
      visible = false
      val a = new EntryPlayerGui(this.controller)
      a.visible = true
  }

  size = new Dimension(200, 300)

  visible = true

}
