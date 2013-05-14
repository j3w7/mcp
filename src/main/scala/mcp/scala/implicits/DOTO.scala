package scala.implicits

object DOTO {

  def doto[A](target: A)(calls: (A => A)*) =
    calls.foldLeft(target) { case (res, f) => f(res) }

  def doto[A](target: A)(calls: (A => Unit)*) =
    calls foreach { _(target) }

}