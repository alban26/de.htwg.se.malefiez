package de.htwg.se.malefiz.aview.gui

import java.awt.{Color, Dimension, Font}

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import de.htwg.se.malefiz.Malefiz.swingGui
import de.htwg.se.malefiz.controller.controllerComponent.ControllerInterface
import de.htwg.se.malefiz.playerModule.JsonSupport
import spray.json.enrichAny

import scala.concurrent.Future
import scala.util.{ Failure, Success }
import scala.concurrent.duration.DurationInt
import scala.swing.event.ButtonClicked
import scala.swing.{Action, Button, Frame, GridBagPanel, Label, Menu, MenuBar, MenuItem, TextField}

class EntryPlayerGui(controller: ControllerInterface) extends Frame with JsonSupport {

  implicit val system = ActorSystem(Behaviors.empty, "Player-Service")
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.executionContext

  title = "Malefiz"
  visible = false
  centerOnScreen()

  val playerLabel: Label = new Label("Enter the names")
  playerLabel.foreground = Color.WHITE
  playerLabel.font = new Font("Sans Serif", Font.BOLD, 22)

  val playerOneLabel: Label = new Label("Player 1   ")
  playerOneLabel.foreground = Color.RED
  playerOneLabel.font = new Font("Sans Serif", Font.ITALIC, 20)
  val playerOneName: TextField = new TextField {
    columns = 15
  }

  val playerTwoLabel: Label = new Label("Player 2   ")
  playerTwoLabel.foreground = Color.GREEN
  playerTwoLabel.font = new Font("Sans Serif", Font.ITALIC, 20)
  val playerTwoName: TextField = new TextField {
    columns = 15
  }

  val playerThreeLabel: Label = new Label("Player 3   ")
  playerThreeLabel.foreground = Color.ORANGE
  playerThreeLabel.font = new Font("Sans Serif", Font.ITALIC, 20)
  val playerThreeName: TextField = new TextField {
    columns = 15
  }

  val playerFourLabel: Label = new Label("Player 4    ")
  playerFourLabel.foreground = Color.BLUE
  playerFourLabel.font = new Font("Sans Serif", Font.ITALIC, 20)
  val playerFourName: TextField = new TextField {
    columns = 15
  }

  val continueButton = new Button("start new game")

  menuBar = new MenuBar {
    contents += new Menu("Malefiz") {
      contents += new MenuItem(Action("Quit") {
        System.exit(0)
      })
    }
  }

  contents = new GridBagPanel {

    background = Color.DARK_GRAY

    def constraints(x: Int,
                    y: Int,
                    gridWidth: Int = 1,
                    gridHeight: Int = 1,
                    weightX: Double = 0.0,
                    weighty: Double = 0.0,
                    ipadX: Int = 0,
                    ipadY: Int = 0,
                    fill: GridBagPanel.Fill.Value = GridBagPanel.Fill.None,
                    anchor: GridBagPanel.Anchor.Value = GridBagPanel.Anchor.Center)
    : Constraints = {
      val c = new Constraints
      c.gridx = x
      c.gridy = y
      c.gridwidth = gridWidth
      c.gridheight = gridHeight
      c.weightx = weightX
      c.weighty = weighty
      c.fill = fill
      c.ipadx = ipadX
      c.ipady = ipadY
      c.anchor = anchor
      c
    }

    add(playerLabel,
      constraints(0, 0, gridWidth = 2, ipadY = 20, fill = GridBagPanel.Fill.Horizontal, anchor = GridBagPanel.Anchor.PageStart))
    add(playerOneLabel,
      constraints(0, 1, ipadY = 20))
    add(playerOneName,
      constraints(1, 1, ipadY = 20))
    add(playerTwoLabel,
      constraints(0, 2, ipadY = 20))
    add(playerTwoName,
      constraints(1, 2, ipadY = 20))
    add(playerThreeLabel,
      constraints(0, 3, ipadY = 20))
    add(playerThreeName,
      constraints(1, 3, ipadY = 20))
    add(playerFourLabel,
      constraints(0, 4, ipadY = 20))
    add(playerFourName,
      constraints(1, 4, ipadY = 20))
    add(continueButton,
      constraints(0, 8, gridWidth = 2, ipadY = 20, anchor = GridBagPanel.Anchor.South))
  }

  listenTo(continueButton)

  reactions += {
    case ButtonClicked(`continueButton`) =>
      val pList = List(playerOneName.text, playerTwoName.text, playerThreeName.text, playerFourName.text)
      //val playerList = createPlayerList(pList, 0)

      /*  val playerList : List[Player] = List(new Player(0, pList(0)),
          new Player (1, pList(1)),
          new Player (2, pList(2)),
          new Player(3, pList(3)));

       */

      /*
      pList.indic.foreach(index => {
        if(pList(index) != "") {
          playerList.appended(new Player(index, pList(index)))
        }
      })

       */

      val setPlayersRequest = HttpRequest(
        method = HttpMethods.POST,
        uri = "http://localhost:8080/players",
        entity = HttpEntity(
          ContentTypes.`application/json`,
          pList.toJson.toString()
        )
      )
      sendRequest(setPlayersRequest).foreach(println)

      pList.indices.foreach(x => if (pList(x) != "") controller.execute("n " + pList(x)))
      controller.execute("start")

  }

  def sendRequest(request: HttpRequest): Future[String] = {
    val responseFuture: Future[HttpResponse] = Http().singleRequest(request)
    responseFuture.onComplete {
      case Success(value) =>
        println(value)
        this.visible = false
        swingGui.visible = true
        swingGui.updatePlayerArea()
        swingGui.drawGameBoard()
        swingGui.updatePlayerTurn()
        swingGui.updateInformationArea()
      case Failure(_) => sys.error("something went wrong")
    }
    val entityFuture: Future[HttpEntity.Strict] = responseFuture.flatMap(response => response.entity.toStrict(2.seconds))
    entityFuture.map(entity => entity.data.utf8String)
  }

  size = new Dimension(500, 500)
}
