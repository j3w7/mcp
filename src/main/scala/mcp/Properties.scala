package mcp

import com.typesafe.config.ConfigFactory

object Properties {

  val conf = ConfigFactory.load("services.properties");

  def property(path: String): String = conf.getString(path)

}