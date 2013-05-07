package scallop

import scala.collection.JavaConversions.asScalaBuffer

import twitter4j.TwitterFactory

object TwitterTest extends App {

  val twitter = TwitterFactory.getSingleton();

  println("tweets:")
  for (status ← twitter.getHomeTimeline()) {
    println("[" + status.getUser().getName() + "]\t" +
      status.getText());
    // status.getURLEntities()
  }
}