package de.htwg.se.malefiz.aview.gui

import java.awt.{BasicStroke, Color}
import java.awt.image.BufferedImage
import java.io.File

import de.htwg.se.malefiz.controller.GameStates.SelectFigure
import de.htwg.se.malefiz.controller._
import javax.imageio.ImageIO

import scala.swing.BorderPanel.Position._
import scala.swing._
import scala.swing.event.{ButtonClicked, _}

class SwingGui(controller: Controller) extends Frame {

  val bimage: BufferedImage = ImageIO.read(new File("src/main/scala/de/htwg/se/malefiz/aview/gui/malefizimg.png"))
  val g2d: Graphics2D = bimage.createGraphics()

  listenTo(controller)
  visible = false
  centerOnScreen()
  val playerLabel = new Label("Player")
  val playerTurnLabel = new Label("Player Turn")
  size.height = bimage.getHeight(null)

  title = "Malefiz"
  val playerTurnArea = new TextArea("")
  val cubeButton = new Button("throw Cube")

  val randomNumberLabel = new Label("Cube shows: ")
  val randomNumberArea = new TextArea("")
  playerTurnArea.editable = false
  val panel: Panel = new Panel {

    override def paint(g: Graphics2D): Unit = {
      g.drawImage(bimage, 0, 0, null)
    }

    preferredSize = new Dimension(bimage.getWidth(null), bimage.getHeight())
    listenTo(mouse.clicks)

    reactions += {

      case MouseClicked(_, p, _, 1, _) =>
        mouseX = getRange(p.x)
        mouseY = getRange(p.y)
        val state = controller.s.state

        if (state.isInstanceOf[SelectFigure]) {
          for (i <- controller.gameBoard.cellList) {
            if (mouseX.contains(i.coordinates.x_coordinate) && mouseY.contains(i.coordinates.y_coordinate)) {
              controller.execute(i.playerNumber + " " + i.figureNumber)
              drawGameBoard()
            }
          }
        } else {
          for (i <- controller.gameBoard.cellList) {
            if (mouseX.contains(i.coordinates.x_coordinate) && mouseY.contains(i.coordinates.y_coordinate)) {
              controller.execute(i.cellNumber.toString)
              drawGameBoard()
              updatePlayerTurn()
            }
          }
        }
    }
  }
  val mainPanel = new BorderPanel
  private val thick = new BasicStroke(3f)

  var mouseX: Set[Int] = Set().empty
  var mouseY: Set[Int] = Set().empty
  var playerArea = new TextArea("")

  def getRange(u: Int): Set[Int] = {
    var lowR = u
    var highR = u
    var range: Set[Int] = Set().empty
    for (i <- 0 to 20) {
      lowR = lowR - 1
      range += lowR
    }
    for (i <- 0 to 20) {
      highR = highR + 1
      range += highR
    }
    range
  }

  def updatePlayerArea(): Unit = {
    for (i <- controller.gameBoard.players.indices) {
      this.playerArea.text += "Spieler " + (i + 1) + ": " + controller.gameBoard.players(i) + "\n"
    }
  }

  def updatePlayerTurn(): Unit = {
      this.playerTurnArea.text = controller.playersTurn.name
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

  def drawGameBoard(): Unit = {
    for (i <- controller.gameBoard.cellList) {
      if (i.hasWall) {
        this.drawCircle(i.coordinates.x_coordinate, i.coordinates.y_coordinate, Color.WHITE)
      } else if (i.playerNumber == 1) {
        this.drawCircle(i.coordinates.x_coordinate, i.coordinates.y_coordinate, Color.RED)
      } else if (i.playerNumber == 2) {
        this.drawCircle(i.coordinates.x_coordinate, i.coordinates.y_coordinate, Color.GREEN)
      } else if (i.playerNumber == 3) {
        this.drawCircle(i.coordinates.x_coordinate, i.coordinates.y_coordinate, Color.YELLOW)
      } else if (i.playerNumber == 4) {
        this.drawCircle(i.coordinates.x_coordinate, i.coordinates.y_coordinate, Color.BLUE)
      } else if (i.possibleCells || i.possibleCells && i.playerNumber != 0) {
        this.highlightCells(i.coordinates.x_coordinate, i.coordinates.y_coordinate)
      } else if (!i.possibleCells) {
        this.drawCircle(i.coordinates.x_coordinate, i.coordinates.y_coordinate, Color.BLACK)
      } else {
        this.drawCircle(i.coordinates.x_coordinate, i.coordinates.y_coordinate, Color.BLACK)
      }
    }
  }

  contents = new BorderPanel {
    border = Swing.LineBorder(Color.BLACK, 5)
    layout += panel -> Center
    layout += new GridPanel(3, 1) {
      border = Swing.LineBorder(Color.BLACK)
      contents += new GridPanel(3, 1) {
        border = Swing.LineBorder(Color.BLACK)
        contents += playerLabel
        contents += playerArea
        contents += new GridPanel(2, 1) {
          contents += playerTurnLabel
          contents += playerTurnArea
        }
      }
      contents += new GridPanel(3, 1) {
        border = Swing.LineBorder(Color.BLACK)
        contents += cubeButton
        contents += randomNumberLabel
        contents += randomNumberArea
      }
    } -> East
  }

  def drawCircle(x: Int, y: Int, color: Color): Unit = {
    g2d.setColor(color)
    g2d.fillArc(x - 20, y - 20, 35, 35, 0, 360)
    repaint()
  }

  listenTo(cubeButton)
  reactions += {
    case ButtonClicked(`cubeButton`) =>
      controller.execute("r")
      randomNumberArea.text = "Gewürfelte Nummer = " + controller.dicedNumber.toString
  }

  def highlightCells(x: Int, y: Int): Unit = {
    g2d.setStroke(thick)
    g2d.setColor(Color.CYAN)
    g2d.drawArc(x - 19, y - 19, 32, 32, 0, 360)
    repaint()
  }
}
