package de.htwg.se.malefiz.aview.gui

import java.awt.{Color, Dimension, Font}

import de.htwg.se.malefiz.controller.Controller

import scala.swing.{Action, BorderPanel, Button, FlowPanel, Frame, GridBagPanel, GridPanel, Label, Menu, MenuBar, MenuItem, TextArea, TextField}
import BorderPanel.Position._
import scala.swing.event.ButtonClicked
class EntryPlayerGui(controller: Controller) extends Frame {

  visible = false

  title = "Malefiz"
  background = Color.MAGENTA
  centerOnScreen()

  val playerLabel: Label = new Label("Enter the names")
  playerLabel.foreground = Color.MAGENTA
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

    add(playerLabel,
      constraints(0, 0,fill=GridBagPanel.Fill.Horizontal))
    add(playerOneLabel,
      constraints(0, 1))
    add(playerOneName,
      constraints(0, 2))
    add(playerTwoLabel,
      constraints(0, 3))
    add(playerTwoName,
      constraints(0, 4))
    add(playerThreeLabel,
      constraints(0, 5))
    add(playerThreeName,
      constraints(0, 6))
    add(playerFourLabel,
      constraints(0, 7))
    add(playerFourName,
      constraints(0, 8))
    add(continueButton,
      constraints(0, 9))

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
      controller.gui.visible = true
      controller.gui.updatePlayerArea()
      controller.gui.updatePlayerTurn()
      controller.gui.drawGameBoard()
  }

  size = new Dimension(500, 500)
}
