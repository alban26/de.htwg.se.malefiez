package de.htwg.se.malefiz.aview.gui

import java.awt.image.BufferedImage
import java.awt.{Color, Font, Image}
import java.io.File
import de.htwg.se.malefiz.Malefiz.gui
import de.htwg.se.malefiz.Malefiz.entryGui
import com.google.inject.Inject
import de.htwg.se.malefiz.controller.controllerComponent.ControllerInterface
import javax.imageio.ImageIO
import javax.swing.{ImageIcon, JLabel}

import scala.swing.{Action, BorderPanel, Button, Dimension, Frame, Graphics2D, GridBagPanel, GridPanel, Image, Label, Menu, MenuBar, MenuItem, Panel}
import scala.swing.event.{ButtonClicked, Key}

class EntryGui @Inject() (controller: ControllerInterface) extends Frame {

  visible = true
  title = "Wilkommen zu Malefiz"
  centerOnScreen()


  val welcomeLabel = new Label("Willkommen bei Malefiz")
  welcomeLabel.font = new Font("Sans Serif", Font.BOLD, 22)
  val newGameButton = new Button("New Game")
  val quitButton = new Button("Quit")


  menuBar = new MenuBar{

    contents += new Menu("Malefiz") {
      contents += new MenuItem(Action("Quit") {
        System.exit(0)
      })
    }
    contents += new Menu("Edit") {
      mnemonic = Key.E
      contents += new MenuItem(Action("Load") {
        //close()

        entryGui.visible = false
        //deafTo(controller)
        gui.visible = true
        controller.load
        gui.updateInformationArea()
        gui.updatePlayerArea()
        gui.updatePlayerTurn()
      })
  }}


  contents = new GridBagPanel {

    def constraints(x: Int, y:Int,
            gridwidth: Int = 1, gridheight: Int = 1,
            weightx: Double = 0.0, weighty: Double = 0.0,
            fill: GridBagPanel.Fill.Value = GridBagPanel.Fill.None,
            ipadx: Int = 0, ipady: Int = 0, anchor : GridBagPanel.Anchor.Value = GridBagPanel.Anchor.Center)

    : Constraints = {
      val c = new Constraints
      c.gridx = x
      c.gridy = y
      c.gridwidth = gridwidth
      c.gridheight = gridheight
      c.weightx = weightx
      c.weighty = weighty
      c.fill = fill
      c.ipadx = ipadx
      c.ipady = ipady
      c.anchor = anchor
      c
    }

    add(welcomeLabel,
      constraints(0, 0, gridheight = 2, ipadx= 50, ipady = 50, anchor = GridBagPanel.Anchor.North, fill = GridBagPanel.Fill.Vertical))
    add(newGameButton,
      constraints(0, 2, gridheight = 2,ipadx = 20, ipady = 20))
    add(quitButton,
      constraints(0, 4, gridheight = 2, ipadx = 20, ipady = 20))

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
