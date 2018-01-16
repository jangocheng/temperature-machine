package bad.robot.temperature.server

import java.io.File

import bad.robot.temperature.{Error, FileError, FileOps}

import scala.io.Source
import scala.{Error => _}
import scalaz.\/
import scalaz.\/.fromTryCatchNonFatal

object JsonFile {

  val path = new File(sys.props("user.home")) / ".temperature"
  val file = path / "temperature.json"

  path.mkdirs()

  
  def exists = file.exists()

  def load: Error \/ String = {
    fromTryCatchNonFatal(Source.fromFile(file).getLines().mkString("\n")).leftMap(FileError)
  }

}