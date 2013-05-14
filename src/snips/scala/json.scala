import com.codahale.jerkson.Json._

object json {
  case class Person(id: Long, name: String)

  def main(args: Array[String]) {

    // Parse JSON arrays
    parse[List[Int]]("[1,2,3]") //=> List(1,2,3)

    // Parse JSON objects
    parse[Map[String, Int]]("""{"one":1,"two":2}""") //=> Map("one"->1,"two"->2)

    // Parse JSON objects as case classes
    // (Parsing case classes isn't supported in the REPL.)
    println(parse[Person]("""{"id":1,"name":"Coda"}""")) //=> Person(1,"Coda")

    // Parse streaming arrays of things
    //  for (person ← stream[Person](inputStream)) {
    //    println("New person: " + person)
    //  }

  }
}
