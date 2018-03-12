package it.nowicki.jaroslaw.other


object FoldLeftList {
  def foldLeft[A, B](xs: List[A], b: B, f: (B, A) => B) = xs.foldLeft(b)(f)
}

trait FoldLeft[F[_]] {
  def foldLeft[A, B](xs: F[A], b: B, f: (B, A) => B) : B
}

object FoldLeft {
  implicit val foldLeftList: FoldLeft[List] = new FoldLeft[List] {
    override def foldLeft[A, B](xs: List[A], b: B, f: (B, A) => B): B = xs.foldLeft(b)(f)
  }
}



object d3 extends App {

  def sum[A: Monoid](xs: List[A]): A = {
    val m = implicitly[Monoid[A]]
    FoldLeftList.foldLeft(xs, m.mzero, m.mappend)
  }

  def sum2[M[_]: FoldLeft, A: Monoid](xs: M[A]): A = {
    val m = implicitly[Monoid[A]]
    val fl = implicitly[FoldLeft[M]]
    fl.foldLeft(xs, m.mzero, m.mappend)
  }

  override def main(args: Array[String]): Unit = {
    val xInt = List(1, 2, 3, 4)
    val xString = List("dziala", "nie dziala")

    println(sum(xInt))
    println(sum(xString))
    println(sum2(xInt))

  }


}
