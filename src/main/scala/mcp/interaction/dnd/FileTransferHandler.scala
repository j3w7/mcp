package mcp.interaction.dnd
import java.awt.datatransfer.Transferable
import javax.swing.TransferHandler
import java.awt.datatransfer.DataFlavor
import java.awt.Toolkit
import javax.swing.JComponent
import java.io.File
import scala.collection.JavaConversions._
import java.io.IOException
import java.net.MalformedURLException
import java.awt.datatransfer.UnsupportedFlavorException
import java.net.URL

class FileTransferHandler extends TransferHandler {

  def canImport(compo: JComponent, flavors: List[DataFlavor]): Boolean = {
    for (flavor ← flavors) {
      flavor match {
        case DataFlavor.javaFileListFlavor ⇒
          println("canImport: JavaFileList FLAVOR: " + flavor)
          return true
        case DataFlavor.stringFlavor ⇒
          println("canImport: String FLAVOR: " + flavor)
          return true
        case _ ⇒
          System.err.println("canImport: Rejected Flavor: " + flavor)
          return false
      }
    }
    return false
  }

  override def importData(comp: JComponent, t: Transferable): Boolean = {
    val flavors = t.getTransferDataFlavors()
    println("Trying to import:" + t)
    println("... which has " + flavors.length + " flavors.")
    for (flavor ← flavors) {
      try {
        flavor match {
          case DataFlavor.javaFileListFlavor ⇒
            println("importData: FileListFlavor")

            val l = t.getTransferData(DataFlavor.javaFileListFlavor).asInstanceOf[java.util.List[File]]
            for (file ← l)
              println("GOT FILE: " + file.getCanonicalPath())

            true

          case DataFlavor.stringFlavor ⇒
            println("importData: String Flavor")
            val fileOrURL = "" + t.getTransferData(flavor)
            println("GOT STRING: " + fileOrURL)
            try {
              val url = new URL(fileOrURL)
              println("Valid URL: " + url.toString())
              // Do something with the contents...
              true
            } catch {
              case ex: MalformedURLException ⇒
                System.err.println("Not a valid URL")
                false
            }
          // now do something with the String.

          case _ ⇒
            println("importData rejected: " + flavor)
            // Don't return try next flavor.
            false

        }
      } catch {
        case e: IOException ⇒
          System.err.println("IOError getting data: " + e)
        case e: UnsupportedFlavorException ⇒
          System.err.println("Unsupported Flavor: " + e)
          false
      }
    }
    // If you get here, I didn't like the flavor.
    Toolkit.getDefaultToolkit().beep()
    false
  }
}
