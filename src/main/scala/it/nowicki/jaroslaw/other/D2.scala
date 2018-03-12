package it.nowicki.jaroslaw.other

trait Monoid[A] {
  def mappend(a1: A, a2: A): A
  def mzero: A
}

object IntMonoid extends Monoid[Int] {
  def mappend(a: Int, b: Int): Int = a + b
  def mzero: Int = 0
}


object Monoid {
  implicit val IntMonoid: Monoid[Int] = new Monoid[Int] {
    def mappend(a: Int, b: Int): Int = a + b
    def mzero: Int = 0
  }

  implicit val StringMonoid: Monoid[String] = new Monoid[String] {
    override def mappend(a: String, b: String): String = a + b
    override def mzero: String = ""
  }
}

object D2 extends App {

  override def main(args: Array[String]): Unit = {

    val test = List(1, 2, 3, 4)

    println(sum(test))
    println(sum1(test))
    println(sum2(test, IntMonoid))

    implicit val intMonoid = IntMonoid

    println(sum3(test))

  }

  def sum(xs: List[Int]) : Int = xs.foldLeft(0)(_ + _)

  def sum1(xs: List[Int]) : Int = xs.foldLeft(IntMonoid.mzero)(IntMonoid.mappend)

  def sum2[A](xs: List[A], m: Monoid[A]) : A = xs.foldLeft(m.mzero)(m.mappend)

  def sum3[A](xs: List[A])(implicit m: Monoid[A]) : A = xs.foldLeft(m.mzero)(m.mappend)

}
