package de.htwg.se.malefiz.aview
import com.google.inject.{Guice, Injector}
import de.htwg.se.malefiz.MalefizModule
import de.htwg.se.malefiz.controller.controllerComponent.ControllerInterface
import de.htwg.se.malefiz.rest.restComponent.restBaseImpl.RestController
import org.scalatest._
import org.scalatest.matchers.should.Matchers

class TuiSpec extends WordSpec with Matchers {

  "A Malefiz Tui" when {
    "when a new game start" should {


      val injector: Injector = Guice.createInjector(new MalefizModule)
      val controller: ControllerInterface = injector.getInstance(classOf[ControllerInterface])

      val tui = new Tui(controller)

      "You can load a existing game " in {
        tui.runInput("load")

        val playerList = List("Alban", "Robert")

        controller.load() should be (true)

      }
      "or can start a new Game with " in {
        tui.runInput("start")

        controller.startGame() should be (true)
      }
    }
  }
}
