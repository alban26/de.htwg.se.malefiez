package de.htwg.se.malefiz.aview.gui

import java.awt.{Color, Font}

import com.google.inject.Inject
import de.htwg.se.malefiz.controller.controllerComponent.ControllerInterface

import scala.swing.{Action, Button, Dimension, Frame, GridBagPanel, Label, Menu, MenuBar, MenuItem}
import scala.swing.event.ButtonClicked

class EntryGui @Inject() (controller: ControllerInterface) extends Frame {

  visible = true
  title = "Wilkommen zu Malefiz"
  centerOnScreen()

  background = Color.RED
  val welcomeLabel = new Label("Willkommen bei Malefiz")
  welcomeLabel.foreground = Color.MAGENTA
  welcomeLabel.font = new Font("Sans Serif", Font.BOLD, 22)
  val newGameButton = new Button("New Game")
  val quitButton = new Button("Quit")

  menuBar = new MenuBar{

    contents += new Menu("Malefiz") {
      contents += new MenuItem(Action("Quit") {
        System.exit(0)
      })
    }
  }

  contents = new GridBagPanel {
    def constraints(x: Int, y:Int,
            gridwidth: Int = 1, gridheight: Int = 1,
            weightx: Double = 0.0, weighty: Double = 0.0,
            fill: GridBagPanel.Fill.Value = GridBagPanel.Fill.None)
    : Constraints = {
      val c = new Constraints
      c.gridx = x
      c.gridy = y
      c.gridwidth = gridwidth
      c.gridheight = gridheight
      c.weightx = weightx
      c.weighty = weighty
      c.fill = fill
      c
    }

    add(welcomeLabel,
      constraints(0, 0))
    add(newGameButton,
      constraints(0, 1,fill=GridBagPanel.Fill.Horizontal ))
    add(quitButton,
      constraints(0, 2))
  }

 listenTo(newGameButton, quitButton)

  reactions += {
    case ButtonClicked(`quitButton`) => System.exit(0)
    case ButtonClicked(`newGameButton`) =>
      visible = false
      val a = new EntryPlayerGui(this.controller)
      a.visible = true
  }
 size = new Dimension(500, 500)
}
