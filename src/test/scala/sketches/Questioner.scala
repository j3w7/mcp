package sketches

import scala.collection.Iterable

trait Item {}

class Questioner {

  //FIXME
  def questionEverything() = {
    val everything: Iterable[Item] = null //TODO get everything from DB
    for (thing ← everything) {
      question(thing)
    }
  }

  def question(item: Item) {
    // FIXME val report = introspect(item)
    // FIXME print(report)
  }
}