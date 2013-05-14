package scala.implicits

object SideEffects {
  implicit def anyWithSideEffects[T](any: T) = new {
    def ~(fn: T => Unit) = {
      fn(any)
      any
    }
  }
}
