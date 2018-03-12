package it.nowicki.jaroslaw.other

import simulacrum._

@typeclass trait Show[A] { self =>
  def show(a: A) : String
}


object Show {

  //  def show[A](s: A)(implicit sh: Show[A]) = sh.show(s)
  //
  def show[A](f: A => String) : Show[String] = new Show[String] {
    override def show(a: String): String = ???
  }


}



object App1 extends  App {








  //  import Show._

  override def main(args: Array[String]): Unit = {
//    println("29".show)
  }
}