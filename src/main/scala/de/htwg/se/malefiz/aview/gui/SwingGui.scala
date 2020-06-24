package de.htwg.se.malefiz.aview.gui

import java.awt.{Image, Toolkit}

import de.htwg.se.malefiz.controller._
import javax.imageio.ImageIO
import scala.swing.event.ButtonClicked

import scala.swing.event._
import scala.swing._
import scala.io.Source._
import javax.swing.ImageIcon
import java.io.File
import BorderPanel.Position._

class SwingGui(controller: Controller) extends Frame {


  listenTo(controller)

  val bimage = ImageIO.read(new File("src/main/scala/de/htwg/se/malefiz/aview/gui/malefizimg.png"))
  val g2d = bimage.createGraphics()
  size.height = bimage.getHeight(null)

  title = "Malefiz"
  var playerArea = new TextArea("")
  playerArea.editable = false
  val informationArea = new TextArea("")
  informationArea.editable = false

  val cubeButton = new Button("throw Cube")




  val panel = new Panel {

    override def paint(g: Graphics2D) = {
      g.drawImage(bimage, 0, 0, null)
    }
    preferredSize = new Dimension(758, 768)

  }


  def drawCircle(x: Int, y: Int, color: Color): Unit = {
    g2d.setColor(color)
    g2d.setStroke(bs)
    g2d.drawArc(x - 15, y - 15, 30, 30, 0, 360)
    repaint()
  }

  menuBar = new MenuBar {
    contents += new Menu("Malefiz") {
      mnemonic = Key.F
      contents += new MenuItem(Action("Quit") {
        System.exit(0)
      })
    }
    contents += new Menu("Edit") {
      mnemonic = Key.E
      contents += new MenuItem(Action("Undo") {
        controller.undo
      })
      contents += new MenuItem(Action("Redo") {
        controller.redo
      })
    }
  }





  contents = new SplitPane(Orientation.Vertical,
    panel,
    new GridPanel(4,1) {
      contents += playerArea
      contents += cubeButton
      contents += informationArea
  })


  def updatePlayerArea = {
    for (i <- controller.gameBoard.players.indices) {
      playerArea.text +=  "Spieler "+ (i+1) + ": " + controller.gameBoard.players(i) + "\n"
    }
    repaint
  }

  listenTo(cubeButton)
  reactions += {
    case ButtonClicked(`cubeButton`) =>
      controller.execute("r")
      informationArea.text = controller.dicedNumber.toString

      repaint()
  }


  visible = false
  centerOnScreen()

}
