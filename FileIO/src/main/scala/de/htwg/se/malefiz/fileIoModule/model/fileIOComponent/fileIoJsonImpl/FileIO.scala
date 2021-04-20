package de.htwg.se.malefiz.fileIoModule.model.fileIOComponent.fileIoJsonImpl

import de.htwg.se.malefiz.fileIoModule.model.FileIOInterface
import scala.io.Source
import scala.util.control.Breaks.break
import scala.util.{Failure, Success, Try}

class FileIO extends FileIOInterface {

  def load: String = {
    fileNotFound("FileIO/src/main/scala/de/htwg/se/malefiz/fileIoModule/gameboard.json") match {
      case Success(v) => println("File Found")
      case Failure(v) => println("File not Found")
        break
    }
    val file = Source.fromFile("FileIO/src/main/scala/de/htwg/se/malefiz/fileIoModule/gameboard.json")
    try file.mkString finally file.close()
  }

  def fileNotFound(filename: String): Try[String] = {
    Try(Source.fromFile(filename).getLines().mkString)
  }

  def save(gamestate_json: String): Unit = {
    import java.io._
    val print_writer = new PrintWriter(new File("FileIO/src/main/scala/de/htwg/se/malefiz/fileIoModule/gameboard.json"))
    print_writer.write(gamestate_json)
    print_writer.close()
  }

}

