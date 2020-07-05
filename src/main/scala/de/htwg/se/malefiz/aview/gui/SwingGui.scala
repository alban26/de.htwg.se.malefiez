package de.htwg.se.malefiz.aview.gui

import java.awt.{BasicStroke, Color, Font}
import java.awt.image.BufferedImage
import java.io.File

import com.google.inject.Inject
import de.htwg.se.malefiz.controller.controllerComponent
import de.htwg.se.malefiz.controller.controllerComponent.GameStates.SelectFigure
import de.htwg.se.malefiz.controller.controllerComponent.{ControllerInterface, GameBoardChanged, Request, StatementRequest, Statements, Winner}
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.text.StyleConstants

import scala.swing._
import scala.swing.event.{ButtonClicked, _}

class SwingGui @Inject() (controller: ControllerInterface) extends Frame {

  val bimage: BufferedImage = ImageIO.read(new File("src/main/scala/de/htwg/se/malefiz/aview/gui/malefizimg.png"))
  val g2d: Graphics2D = bimage.createGraphics()

  title = "Malefiz"
  centerOnScreen()
  visible = false

  val playerLabel = new Label("Player")
  playerLabel.font = new Font("Sans Serif", Font.BOLD, 18)
  playerLabel.border = Swing.EtchedBorder(Swing.Lowered)

  var playerArea = new TextPane
  playerArea.font = new Font("Sans Serif", Font.CENTER_BASELINE, 16)
  playerArea.border = Swing.EtchedBorder(Swing.Lowered)
  playerArea.editable = false

  val playerTurnLabel = new Label("Player Turn")
  playerTurnLabel.border = Swing.EtchedBorder(Swing.Lowered)
  playerTurnLabel.font = new Font("Sans Serif", Font.BOLD, 18)

  val playerTurnArea = new TextArea("")
  playerTurnArea.border = Swing.EtchedBorder(Swing.Lowered)
  playerTurnArea.font = new Font("Sans Serif", Font.CENTER_BASELINE, 16)
  playerTurnArea.editable = false

  val cubeLabel = new Label("Dice")
  cubeLabel.font = new Font("Sans Serif", Font.BOLD, 18)
  cubeLabel.border = Swing.EtchedBorder(Swing.Lowered)

  val cubeButton = new Button()
  val cubeIcon = new ImageIcon("project/dice.png")
  cubeButton.icon = cubeIcon
  cubeButton.border = Swing.EtchedBorder(Swing.Lowered)
  cubeButton.font = new Font("Sans Serif", Font.BOLD, 18)

  val randomNumberLabel = new Label("Cube shows: ")
  randomNumberLabel.font = new Font("Sans Serif", Font.BOLD, 18)
  randomNumberLabel.border = Swing.EtchedBorder(Swing.Lowered)

  val randomNumberArea = new TextArea(" ")
  randomNumberArea.font = new Font("Sans Serif", Font.BOLD, 18)
  randomNumberArea.border = Swing.EtchedBorder(Swing.Lowered)

  val informationArea = new TextArea("")
  informationArea.font = new Font("Sans Serif", Font.CENTER_BASELINE, 18)
  informationArea.border = Swing.EtchedBorder(Swing.Lowered)
  informationArea.editable = false

  def updatePlayerArea(): Unit = {
    var doc = playerArea.styledDocument

    for (i <- controller.getPlayer.indices) {
      val playerString = " Spieler" + (i + 1) + ": " + controller.getPlayer(i) + "\n"
      i match {
        case 0 =>
          var a = playerArea.styledDocument.addStyle("Red" ,null)
          StyleConstants.setForeground(a,Color.RED)
          doc.insertString(doc.getLength,playerString, a)
        case 1 =>
          var b = playerArea.styledDocument.addStyle("Green",null)
          StyleConstants.setForeground(b,Color.GREEN)
          doc.insertString(doc.getLength,playerString, b)
        case 2 =>
          var c = playerArea.styledDocument.addStyle("Yellow/Orange",null)
          StyleConstants.setForeground(c,Color.ORANGE)
          doc.insertString(doc.getLength,playerString, c)
        case 3 =>
          var d = playerArea.styledDocument.addStyle("Blue",null)
          StyleConstants.setForeground(d,Color.BLUE)
          doc.insertString(doc.getLength,playerString, d)
      }
    }
  }

  def updatePlayerTurn(): Unit = {
    this.playerTurnArea.text = "\n" + "             " + controller.getPlayersTurn.name
  }

  def updateInformationArea(): Unit = this.informationArea.text = Statements.value(controllerComponent.StatementRequest(controller))


  def drawGameBoard(): Unit = {
    for (i <- controller.getCellList) {
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
      } else {
        this.drawCircle(i.coordinates.x_coordinate, i.coordinates.y_coordinate, Color.BLACK)
      }
    }
    for (i <- controller.getCellList) {
      if (i.possibleCells && i.playerNumber != controller.getPlayersTurn.playerNumber) {
        this.highlightCells(i.coordinates.x_coordinate, i.coordinates.y_coordinate)
      }
    }
  }

  def drawCircle(x: Int, y: Int, color: Color): Unit = {
    g2d.setColor(color)
    g2d.fillArc(x - 20, y - 20, 35, 35, 0, 360)
    repaint()
  }

  def highlightCells(x: Int, y: Int): Unit = {
    g2d.setStroke(thick)
    g2d.setColor(Color.CYAN)
    //g2d.drawRect(x-10,y-10 , 10, 10)
    g2d.drawArc(x - 16, y - 17, 29, 30, 0, 360)
    repaint()
  }

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
        val state = controller.getGameState.state


        if (state.isInstanceOf[SelectFigure]) {
          for (i <- controller.getCellList) {
            if (mouseX.contains(i.coordinates.x_coordinate) && mouseY.contains(i.coordinates.y_coordinate)) {
              controller.execute(i.playerNumber + " " + i.figureNumber)
              drawGameBoard()
              updateInformationArea()
            }
          }
        } else {
          for (i <- controller.getCellList) {
            if (mouseX.contains(i.coordinates.x_coordinate) && mouseY.contains(i.coordinates.y_coordinate)) {
              controller.execute(i.cellNumber.toString)
              drawGameBoard()
              updatePlayerTurn()
              updateInformationArea()
            }
          }
        }
    }
  }

  private val thick = new BasicStroke(3f)

  var mouseX: Set[Int] = Set().empty
  var mouseY: Set[Int] = Set().empty

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
      constraints(0, 1, gridwidth = 2, fill=GridBagPanel.Fill.Both, ipadx = 104, ipady = 15))
    add(playerArea,
      constraints(0, 2, gridwidth = 2, fill=GridBagPanel.Fill.Both))
    add(playerTurnLabel,
      constraints(2, 1,  gridwidth = 2, fill=GridBagPanel.Fill.Both ,ipadx = 104, ipady =  15))
    add(playerTurnArea,
      constraints(2, 2,gridwidth = 2, fill=GridBagPanel.Fill.Both ))
    add(cubeLabel,
      constraints(4, 1, fill=GridBagPanel.Fill.Both, ipadx = 104, ipady = 15))
    add(cubeButton,
      constraints(4, 2))
    add(randomNumberLabel,
      constraints(6, 1, gridwidth = 2, fill=GridBagPanel.Fill.Both,ipadx= 104, ipady = 15))
    add(randomNumberArea,
      constraints(6, 2, gridwidth = 2, fill=GridBagPanel.Fill.Both))
    add(informationArea,
      constraints(0, 3, gridwidth = 8, fill=GridBagPanel.Fill.Both, ipady = 35))
    add(panel,
      constraints(0,
        0,
        gridwidth = 9,
        ipadx = bimage.getWidth(null),
        ipady = bimage.getHeight(null),
      ))
  }

  listenTo(cubeButton, controller)

  reactions += {
    case ButtonClicked(`cubeButton`) =>
      controller.execute("r")
      randomNumberArea.text = "\n" + "                " + controller.getDicedNumber.toString
      updateInformationArea()
    case gameBoardChanged: GameBoardChanged =>
      drawGameBoard()

    case winner: Winner =>
      drawGameBoard()
      Dialog.showConfirmation(contents.head, Statements.value(StatementRequest(controller)), optionType = Dialog.Options.Default)
      controller.resetGameboard
      playerArea.text = ""
      visible = false
      controller.getEntryGui.visible = true
  }

}
