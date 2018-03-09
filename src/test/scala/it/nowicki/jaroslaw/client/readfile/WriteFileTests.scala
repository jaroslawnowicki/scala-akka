package it.nowicki.jaroslaw.client.readfile

import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter

/**
  * Created by jarek on 06.03.18.
  */
class WriteFileTests extends FunSuite with BeforeAndAfter{


  val tempChunk = "temp"
  val tempChunk1 = "temp_1"
  val tempChunk2 = "temp_2"


  var writeFile: WriteFile = _

  before {
    writeFile = new WriteFile(tempChunk)
  }

  test("get number chunk file") {
    assert (writeFile.createNameFile(1) == 1)
    assert (writeFile.createNameFile(2) == 2)
  }




}
