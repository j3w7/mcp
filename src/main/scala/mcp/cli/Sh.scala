package mcp.cli

// TODO How to check if alias overrides original command behaviour? Is there a way to call unaliased commands (e.g /usr/bin...)? 

object Sh {

  def ls = new ShCmd("ls");

  def getDirectory = new ShParam("-d")

}  