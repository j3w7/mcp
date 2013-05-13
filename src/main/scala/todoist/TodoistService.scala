package todoist

import scala.io.Source.fromURL
import scalaj.http.Http
import java.io.InputStreamReader
import net.liftweb.json.JsonParser
import java.io.InputStream
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import net.liftweb.json.JsonAST.JValue
import scalaj.http.HttpOptions
import net.liftweb.json.JsonAST.JString
import scalaj.http.Http.Request

class TodoistService {

  val conf = ConfigFactory.load("todoist.properties");

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

  def asJSON(is: InputStream) =
    JsonParser.parse(new InputStreamReader(is))

  def login(email: String, password: String): JValue =
    httpGet(_login)
      .param("email", email)
      .param("password", password)(asJSON)

  def connect() = {
    val result = login(
      conf.getString("todoist.email"),
      conf.getString("todoist.password")
    )
    val JString(token) = result \ "api_token"
    this.token = token
  }

  def get() = httpGetTokenized(_get)(asJSON)

}

@Deprecated // for testing
object TodoistService extends App {
  val todoist = new TodoistService();
  todoist.connect()
  print(todoist.get())

}
