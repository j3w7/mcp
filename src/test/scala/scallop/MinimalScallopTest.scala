package scallop

import org.rogach.scallop.Scallop

object MinimalScallopTest extends App {
  while (true) {
    val cmd = readLine
    val opts = Scallop()
      .opt[Boolean]("apples")
      .args(cmd.split(" "))
      .verify

  }
}