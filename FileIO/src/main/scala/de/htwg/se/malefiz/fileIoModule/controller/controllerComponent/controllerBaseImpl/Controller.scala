package de.htwg.se.malefiz.fileIoModule.controller.controllerComponent.controllerBaseImpl

import com.google.inject.{Guice, Inject, Injector}
import de.htwg.se.malefiz.fileIoModule.FileIOServerModule
import de.htwg.se.malefiz.fileIoModule.controller.controllerComponent.ControllerInterface
import de.htwg.se.malefiz.fileIoModule.model.FileIOInterface
import net.codingwell.scalaguice.InjectorExtensions.ScalaInjector

import scala.swing.Publisher

class Controller @Inject() extends ControllerInterface with Publisher {


  val injector: Injector = Guice.createInjector(new FileIOServerModule)
  val fileIo: FileIOInterface = injector.instance[FileIOInterface]


  override def load(): Unit = fileIo.load

  override def save(body: String, suffix: String): Unit = fileIo.save(body, suffix)

}