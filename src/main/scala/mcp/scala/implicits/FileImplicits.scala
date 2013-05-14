package mcp.scala.implicits

import java.io.File
import java.io.FileOutputStream
import java.io.FileInputStream

object FileImplicits {

  implicit def newFile: String ⇒ File = new File(_)

  implicit def newFileOutputStream(s: String): FileOutputStream = new FileOutputStream(s)
  implicit def newFileOutputStream(f: File): FileOutputStream = new FileOutputStream(f)

  implicit def newFileInputStream(s: String): FileInputStream = new FileInputStream(s)
  implicit def newFileInputStream(f: File): FileInputStream = new FileInputStream(f)

}