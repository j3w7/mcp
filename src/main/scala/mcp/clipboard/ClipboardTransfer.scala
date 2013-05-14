package mcp.clipboard

import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.ClipboardOwner
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.StringSelection
import java.awt.datatransfer.Transferable
import java.awt.Toolkit

abstract class FlavorExtractor[T](val flavor: DataFlavor) {
  def extract: Object ⇒ T
}

object StringFlavorExtractor extends FlavorExtractor[String](DataFlavor.stringFlavor) {
  def extract = _.toString
}

class ClipboardTransfer extends ClipboardOwner {

  def lostOwnership(c: Clipboard, t: Transferable) = Unit

  def setClipboardContents(aString: String) = {
    val stringSelection = new StringSelection(aString)
    val clipboard = Toolkit.getDefaultToolkit().getSystemClipboard()
    clipboard.setContents(stringSelection, this)
  }

  def getClipboardContents[T](fx: FlavorExtractor[T]): Option[T] = {
    val contents = Toolkit.getDefaultToolkit.getSystemClipboard.getContents()

    if (!contents.isDataFlavorSupported(DataFlavor.stringFlavor))
      None
    else
      Some(fx.extract(contents.getTransferData(fx.flavor)))
  }

}

object ClipboardTransfer {
  // FIXME This should go to a unittest 
  def main(args: Array[String]) {
    val ClipboardTransfer = new ClipboardTransfer()

    //display what is currently on the clipboard
    println("Clipboard contains:" + ClipboardTransfer.getClipboardContents(StringFlavorExtractor))
  }
}
