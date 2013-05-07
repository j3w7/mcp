package scallop

import org.rogach.scallop.Scallop
import org.rogach.scallop.ScallopOption
import org.rogach.scallop.ScallopConf

object Conf extends ScallopConf(List("-c", "3", "-E", "fruit=apple", "7.2")) {
  // all options that are applicable to builder (like description, default, etc)
  // are applicable here as well
  val count: ScallopOption[Int] = opt[Int]("count", descr = "count the trees", required = true)
    .map(1+) // also here work all standard Option methods -
  // evaluation is deferred to after option construction
  val properties = props[String]('E')
  // types (:ScallopOption[Double]) can be omitted, here just for clarity
  val size: ScallopOption[Double] = trailArg[Double](required = false)
}

object MinimalScallopTest extends App {

  val cmd = readLine
  // that's it. Completely type-safe and convenient.
  assert(Conf.count() == 4)
  assert(Conf.properties("fruit") == Some("apple"))
  assert(Conf.size.get == Some(7.2))
  // passing into other functions
  def someInternalFunc(conf: Conf.type) {
    assert(conf.count() == 4)
  }
  someInternalFunc(Conf)

}