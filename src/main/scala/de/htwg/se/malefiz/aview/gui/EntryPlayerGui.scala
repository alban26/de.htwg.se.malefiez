package de.htwg.se.malefiz.aview.gui

import java.awt.{Color, Dimension}

import de.htwg.se.malefiz.controller.Controller

import scala.swing.{Action, BorderPanel, Button, FlowPanel, Frame, GridPanel, Label, Menu, MenuBar, MenuItem, TextArea, TextField}
import BorderPanel.Position._
import scala.swing.event.ButtonClicked
class EntryPlayerGui(controller: Controller) extends Frame {

  visible = false

  title = "Malefiz"

  val playerOneLabel: Label = new Label("Player 1")
  val playerOneName: TextField = new TextField {columns = 15}

  val playerTwoLabel: Label = new Label("Player 2")
  val playerTwoName: TextField = new TextField {columns = 15}

  val playerThreeLabel: Label = new Label("Player 3")
  val playerThreeName: TextField = new TextField {columns = 15}

  val playerFourLabel: Label = new Label("Player 4")
  val playerFourName: TextField = new TextField {columns = 15}

  val continueButton = new Button("continue")

  menuBar = new MenuBar{
    contents += new Menu("Malefiz") {
      contents += new MenuItem(Action("Quit") {
        System.exit(0)
      })
    }
  }

  contents = new GridPanel(5,1) {
    contents += new FlowPanel {
      contents += playerOneLabel
      contents += playerOneName
    }
    contents += new FlowPanel {
      contents +=  playerTwoLabel
      contents += playerTwoName
    }
    contents += new FlowPanel() {
      contents += playerThreeLabel
      contents += playerThreeName
    }
    contents += new FlowPanel() {
      contents +=  playerFourLabel
      contents +=  playerFourName
    }
    contents += continueButton
  }

  size = new Dimension(300, 500)

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
}
