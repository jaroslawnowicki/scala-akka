package it.nowicki.jaroslaw.other



object D1 extends App {
  override def main(args: Array[String]): Unit = {
    val days = new Days

    days.d1
  }
}


class Days {

  def d1: Unit = {
    def head[A](xs: List[A]): A = xs(0)
    val x = head(1 :: 2 :: Nil)
    case class Car(make: String)

    head(Car("Civic") :: Car("CR-V") :: Nil)



  }

}