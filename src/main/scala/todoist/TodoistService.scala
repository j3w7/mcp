package todoist

import java.io.InputStream
import java.io.InputStreamReader

import scala.io.Source.fromURL

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory

import net.liftweb.json._
import net.liftweb.json.Serialization._
import scalaj.http.Http
import scalaj.http.Http.Request
import scalaj.http.HttpOptions
import mcp.Properties._

class TodoistService {

  val base_url = "https://todoist.com"
  val _login = "/API/login"
  val _get = "/TodoistSync/v2/get"

  var token: String = ""

  def httpGet(command: String) = Http(base_url + command)
    .options(
      HttpOptions.connTimeout(5000),
      HttpOptions.readTimeout(5000)
    )

  def httpGetTokenized: String ⇒ Request = httpGet(_).param("api_token", token)

  def asJSON(is: InputStream) = {
    implicit val formats = DefaultFormats.withHints(ShortTypeHints(List(classOf[Item])))
    JsonParser.parse(new InputStreamReader(is))
  }
  
  def login(email: String, password: String): JValue =
    httpGet(_login)
      .param("email", email)
      .param("password", password)(asJSON)

  def connect() = {
    val result = login(
      property("todoist.email"),
      property("todoist.password")
    )
    val JString(token) = result \ "api_token"
    this.token = token
  }

  def get() = httpGetTokenized(_get)(asJSON)

}

class Item(content: JString)

@Deprecated // for testing
object TodoistService extends App {
  val todoist = new TodoistService();
  todoist.connect()

  val taskDescriptions: JValue = todoist.get() \ "Projects" \ "items" \\ "content"
  print(taskDescriptions)

}
