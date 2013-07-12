package mcp.integrity

import java.security.MessageDigest

import sun.misc.BASE64Encoder

object SHA1 {

  def encode1(string: String): String =
    digest(string.getBytes) map (_ & 0xFF) map (_.toHexString) mkString

  def encode2(string: String): String =
    base64(digest(string.getBytes))

  def digest: Array[Byte] ⇒ Array[Byte] = MessageDigest.getInstance("SHA-1") digest _

  def base64: Array[Byte] ⇒ String = new BASE64Encoder encode _

}