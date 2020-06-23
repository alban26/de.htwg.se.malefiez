package de.htwg.se.malefiz.aview.gui

import java.awt.{Image, Toolkit}

import de.htwg.se.malefiz.controller._
import javax.imageio.ImageIO

import scala.swing.event._
import scala.swing._
import scala.io.Source._
import javax.swing.ImageIcon
import java.io.File

class SwingGui(controller: Controller) extends Frame {


  listenTo(controller)
  val img = ImageIO.read(new File ("project/malefizimg.png"))


  title = "Malefiz"

  val panel = new Panel {
    border = Swing.BeveledBorder(Swing.Raised)
    override def paint(g: Graphics2D) = {
      g.drawImage(img, 0, 0, null)
    }
    preferredSize = new Dimension(758, 768)
  }

/*
  val playerTree = new BorderPanel()
  val outPutTree = new BorderPanel()
  val leftSplit = new SplitPane()
  leftSplit.contents ++  = List(playerTree, outPutTree)
  leftSplit.orientation = Orientation.Vertical
  val rightSplit = new SplitPane()
  rightSplit.orientation = Orientation.Vertical
  rightSplit.contents = panel
  val topSplit = new SplitPane()
  topSplit.contents ++= List(leftSplit, rightSplit)
  topSplit.dividerLocation = 0.3
*/


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

  contents = new BorderPanel {
    add(panel, BorderPanel.Position.Center)
  }

  visible = true
  size = new Dimension(800, 800)
  centerOnScreen()

}
