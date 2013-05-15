package socialmedia

import com.sun.syndication.io.SyndFeedInput
import java.net.URL
import com.sun.syndication.io.XmlReader
import scala.collection.JavaConversions._
import com.sun.syndication.feed.synd.SyndEntryImpl

object RSS extends App {

  def getFeed(url: URL) = new SyndFeedInput().build(new XmlReader(url))

  // test
  
  private val url = new URL("http://feeds.feedburner.com/bingimages")
  private val feed = getFeed(url)

  println("Feed Title: " + feed.getAuthor());

  for (entry ← feed.getEntries.asInstanceOf[java.util.List[SyndEntryImpl]]) {
    println(entry.getTitle())
    println(entry.getUri())
  }

}