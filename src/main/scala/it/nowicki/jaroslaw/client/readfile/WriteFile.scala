package it.nowicki.jaroslaw.client.readfile

import akka.actor.{Actor, PoisonPill, Props}

object WriteFile {

  def props(tempChunkFile: String): Props = Props(new WriteFile(tempChunkFile))

  final case class Message(line: String, inc: Int)
}

class WriteFile(val tempChunkFile: String) extends Actor {

  import java.io._

  import Control._

  val availableThread = 4


  def createNameFile(inc: Int): String = tempChunkFile + moduloFileName(inc)

  def writeFile(nameFile: String, data: String) = {
    val file = new File(nameFile)
    using(new BufferedWriter(new FileWriter(file, true))) {
      bw => bw.write(data + "\n")


        bw.close()
    }

  }

  override def receive: Receive = {
    case WriteFile.Message(line, inc) => writeFile(createNameFile(inc), line)
    case _ => {
      sender() ! PoisonPill
      self ! PoisonPill
    }
  }

  private def moduloFileName(inc: Int) : Int = (1 to availableThread).reverse.find(Math.floorMod(inc, _) == 0).get


}