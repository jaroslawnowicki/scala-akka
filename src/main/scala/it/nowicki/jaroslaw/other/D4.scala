package it.nowicki.jaroslaw.other

import simulacrum._

@typeclass trait MonoId[A] {
  @op("|+|") def mappend(a: A, b: A): A
  def mzero: A

}



object MonoId {

  val syntax = ops

  implicit val intMonoid: MonoId[Int] = new MonoId[Int] {
    override def mappend(a: Int, b: Int): Int = a + b

    override def mzero: Int = 0
  }

  implicit val StringMonoid: MonoId[String] = new MonoId[String] {

    override def mappend(a: String, b: String): String = a + b

    override def mzero: String = ""
  }

}




object D4 extends App  {



  override def main(args: Array[String]): Unit = {

    import MonoId.syntax._

    println(3 |+| 4)
    println("zor" |+| "ro")


  println((Some(1): Option[Int]) != (Some(2): Option[Int]))

    println(1.0 compare 2.0)

    println(1.0 max 2.0)

    println((Right(1): Either[String, Int]) map { _ + 1 })


    println((Left("boom!"): Either[String, Int]) map { _ + 1 })


    val x : Int = 5



  }
}