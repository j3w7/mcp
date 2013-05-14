package slick

import scala.slick.direct.AnnotationMapper.column
import scala.slick.driver.BasicTableComponent.Table

object Coffees extends Table[(String, Int, Double)]("COFFEES") {
  def name = column[String]("COF_NAME", O.PrimaryKey)
  def supID = column[Int]("SUP_ID")
  def price = column[Double]("PRICE")
  def * = name ~ supID ~ price
}

class Coffee {
  def main(args: Array[String]) {

    Coffees.insertAll(
      ("Colombian", 101, 7.99),
      ("Colombian_Decaf", 101, 8.99),
      ("French_Roast_Decaf", 49, 9.99)
    )

    val q = for {
      c ← Coffees if c.supID === 101
      //                       ^ comparing Rep[Int] to Rep[Int]!
    } yield (c.name, c.price)

    println(q.selectStatement)

    q.foreach { case (n, p) ⇒ println(n + ": " + p) }

  }
}