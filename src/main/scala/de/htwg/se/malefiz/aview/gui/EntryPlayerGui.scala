package de.htwg.se.malefiz.aview.gui

import java.awt.{Color, Dimension, Font}
import com.google.inject.Inject
import de.htwg.se.malefiz.controller.controllerComponent.ControllerInterface
import scala.swing.{Action, Button, Frame, GridBagPanel, Label, Menu, MenuBar, MenuItem, TextField}
import scala.swing.event.ButtonClicked

class EntryPlayerGui @Inject() (controller: ControllerInterface) extends Frame {

  visible = false
  title = "Malefiz"
  background = Color.MAGENTA
  centerOnScreen()

  val playerLabel: Label = new Label("Enter the names")
  playerLabel.font = new Font("Sans Serif", Font.BOLD, 22)

  val playerOneLabel: Label = new Label("Player 1   ")
  val playerOneName: TextField = new TextField {columns = 15}

  val playerTwoLabel: Label = new Label("Player 2   ")
  val playerTwoName: TextField = new TextField {columns = 15}

  val playerThreeLabel: Label = new Label("Player 3   ")
  val playerThreeName: TextField = new TextField {columns = 15}

  val playerFourLabel: Label = new Label("Player 4    ")
  val playerFourName: TextField = new TextField {columns = 15}

  val continueButton = new Button("continue")

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
                    fill: GridBagPanel.Fill.Value = GridBagPanel.Fill.None,
                    ipadx: Int = 0 , ipady: Int = 0, anchor : GridBagPanel.Anchor.Value = GridBagPanel.Anchor.Center)
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

    add(playerLabel,
      constraints(0, 0, gridwidth = 2, ipady = 20, fill=GridBagPanel.Fill.Horizontal, anchor = GridBagPanel.Anchor.PageStart))
    add(playerOneLabel,
      constraints(0, 1, ipady = 20))
    add(playerOneName,
      constraints(1, 1, ipady = 20))
    add(playerTwoLabel,
      constraints(0, 2, ipady = 20))
    add(playerTwoName,
      constraints(1, 2, ipady = 20))
    add(playerThreeLabel,
      constraints(0, 3, ipady = 20))
    add(playerThreeName,
      constraints(1, 3, ipady = 20))
    add(playerFourLabel,
      constraints(0, 4, ipady = 20))
    add(playerFourName,
      constraints(1, 4, ipady = 20))
    add(continueButton,
      constraints(0, 8, gridwidth = 2, ipady = 20, anchor = GridBagPanel.Anchor.South ))

  }

  listenTo(continueButton)

  reactions += {
    case ButtonClicked(`continueButton`) =>
      val pList = List(playerOneName.text, playerTwoName.text, playerThreeName.text, playerFourName.text)
      for(i <- pList.indices) {
        if (pList(i) != "")
          controller.execute("n "+ pList(i))
      }
      controller.execute("start")
      this.visible = false
      val sgui = new SwingGui(controller)
      sgui.visible = true
      sgui.updatePlayerArea()
      sgui.updatePlayerTurn()
      sgui.drawGameBoard()
      sgui.updateInformationArea()
  }
  size = new Dimension(500, 500)

}
