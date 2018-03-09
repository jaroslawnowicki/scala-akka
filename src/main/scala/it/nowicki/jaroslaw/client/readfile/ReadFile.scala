package it.nowicki.jaroslaw.client.readfile

class ReadFile {

  import it.nowicki.jaroslaw.client.readfile.Control._

  def read(fileName: String): Option[List[String]] = {
    try {
      val lines = using(io.Source.fromFile(fileName)) { source =>
        (for (line <- source.getLines) yield line).toList
      }
      Some(lines)
    } catch {
      case e: Exception => None
    }
  }

}