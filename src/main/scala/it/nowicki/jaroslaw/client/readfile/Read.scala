package it.nowicki.jaroslaw.client.readfile

import akka.actor.{ActorRef, ActorSystem}

object Read extends App {

  override def main(args: Array[String]): Unit = {

    val system: ActorSystem = ActorSystem("uploadFile")

    val filename = "/home/jarek/Downloads/covtype.data/covtype.data"

    val tempChunkFile: String = "/home/jarek/Downloads/covtype.data/temp/temp_"

    val writeFileActor: ActorRef = system.actorOf(WriteFile.props(tempChunkFile), "writeFileActor")

    val readFile = new ReadFile

    val results = readFile.read(filename)

    var inc = 0



    results foreach { strings =>

      strings.foreach(s => {
        inc +=1
        println(inc)


        val writeFileActor ? "ssa"

      })


    }

  }
}