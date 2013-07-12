package cli
import scala.sys.process.Process

object EnvVarsTest extends App {
  Process(Seq("bash", "echo $asdf"), None, "asdf" -> "Hello, world!").!
}