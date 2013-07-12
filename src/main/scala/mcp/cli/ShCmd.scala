package mcp.cli

import scala.sys.process._

class ShCmd(name: String) {

  def call(cmd: String) = cmd !!

  def call(cmd: String, params: Seq[String]) = Seq(cmd) ++ params !!

}

