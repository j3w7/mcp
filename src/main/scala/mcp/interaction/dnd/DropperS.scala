package mcp.interaction.dnd

import javax.swing.JComponent
import javax.swing.JFrame

/**
 * @deprecated TODO just for testing (move to tests?)
 */
object DropperS {
  def main(args: Array[String]) {
    new DropperS().setVisible(true)
  }
}

/**
 * @deprecated TODO just for testing (move to tests?)
 */
class DropperS extends JFrame("Drop Target") {

  val cp = getContentPane().asInstanceOf[JComponent]
  cp.setTransferHandler(new FileTransferHandler()) // see below

  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  setSize(150, 150)
}

