package mcp.toodledo

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.StringReader
import java.net.MalformedURLException
import java.net.URL
import java.net.URLConnection
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.logging.Level
import java.util.logging.Logger
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import org.xml.sax.SAXException

import mcp.Properties._

class Task {
  var id: String = _
  var title: String = _
  var modified: String = _
  var completed: String = _

  var folder: String = _
  var context: String = _
  var tag: String = _
  var status: String = _
  var priority: String = _
  var length: String = _
  var note: String = _
}

object Toodledo {
  var appID = property("toodledo.appID")
  var userID = property("toodledo.userID")
  var userPW = property("toodledo.userPW")
  var applicationToken = property("toodledo.applicationToken")

  var fields = "folder,context,tag,status,priority,length,note" // get these fields when downloading tasks

  var sessionToken: String = null
  var key: String = null

  var toodledoTokenURL = "https://api.toodledo.com/2/account/token.php"
  var toodledoGetTaskURL = "https://api.toodledo.com/2/tasks/get.php"

  def main(args: Array[String]) {
    md5("fsadf")
    sessionToken = getSessionToken(md5(userID + applicationToken))
    key = md5(md5(userPW) + applicationToken + sessionToken)
    val tasks = getTasks
    // do whatever to tasks
  }

  def getTasks: List[Task] = {
    try {
      val data = "?key=" + key + ";fields=" + fields + ";f=xml"

      println("Getting tasks: " + toodledoGetTaskURL + data)

      val url = new URL(toodledoGetTaskURL + data)
      val connection = url.openConnection()
      connection.setDoOutput(true)

      val in = new BufferedReader(new InputStreamReader(connection.getInputStream()))
      val xml = new StringBuilder()
      var line = in.readLine()
      while (line != null) {
        println(line)
        xml.append(line)
        line = in.readLine()
      }
      in.close()

      val tasks = xmlToTasks(xml.toString())

      for (t ← tasks) {
        println(t.id + " " + t.title)
      }
      return tasks
    } catch {
      case e ⇒ println(e)
    }
    return null
  }

  def xmlToTasks(xml: String): List[Task] = {
    var taskList = List[Task]()

    try {
      val dbf = DocumentBuilderFactory.newInstance()
      val db = dbf.newDocumentBuilder()
      val doc = db.parse(new InputSource(new StringReader(xml)))

      val root = doc.getDocumentElement()

      val tasks = root.getElementsByTagName("task")
      for (i ← 0 to (tasks.getLength - 1)) {
        val task = tasks.item(i).asInstanceOf[Element]

        val t = new Task()
        t.id = getDomTextValue(task, "id")
        t.title = getDomTextValue(task, "title")
        t.modified = getDomTextValue(task, "modified")
        t.completed = getDomTextValue(task, "completed")

        t.folder = getDomTextValue(task, "folder")
        t.context = getDomTextValue(task, "context")
        t.tag = getDomTextValue(task, "tag")
        t.status = getDomTextValue(task, "status")
        t.priority = getDomTextValue(task, "priority")
        t.length = getDomTextValue(task, "length")
        t.note = getDomTextValue(task, "note")
        //FIXME                taskList += t
      }

    } catch {
      //FIXME          case e:ParserConfigurationException => Logger.getLogger(Toodledo.class.getName()).log(Level.SEVERE, null, ex);
      case e: SAXException ⇒ Unit
      case e: IOException  ⇒ Unit
    }

    println("xmlToTasks got " + taskList.size + " tasks")
    return taskList
  }

  def getSessionToken(signature: String): String = {
    try {
      val data = "?userid=" + userID + ";appid=" + appID + ";sig=" + signature + ";f=xml";

      println("Getting session token: " + toodledoTokenURL + data);

      val url = new URL(toodledoTokenURL + data);
      val connection = url.openConnection();
      connection.setDoOutput(true);

      val in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      val xml = in.readLine();
      in.close();
      val token = xml.substring(xml.indexOf("[token]") + 7, xml.indexOf("[/token]"));
      token
    } catch {
      case e: MalformedURLException ⇒ println(e)
      case e: IOException           ⇒ println(e)
    }
    ""
  }

  def md5(input: String): String = {
    try {
      val md = MessageDigest.getInstance("MD5");
      md.update(input.getBytes());

      val byteData = md.digest
      val sb = new StringBuilder();
      for (i ← 0 to byteData.length - 1) {
        sb.append(Integer.toString((byteData(i) & 0xff) + 0x100, 16).substring(1));
      }
      return sb.toString();
    } catch {
      case e: NoSuchAlgorithmException ⇒
        System.err.println("ERROR. MD5 ALGORITHM NOT FOUND")
        return ""
    }
  }

  def getDomTextValue(element: Element, tag: String): String = {
    if (element != null) {
      val nl = element.getElementsByTagName(tag);
      if (nl != null && nl.getLength() > 0) {
        val el = nl.item(0)
        if (el != null && el.getFirstChild() != null) {
          return el.getFirstChild().getNodeValue();
        }
      }
    }
    return "";
  }

}