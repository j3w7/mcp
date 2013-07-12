package cli;

import e.ptextarea.PCaretListener
import e.ptextarea.PTextArea
import javax.swing.JFrame
import scala.swing.Swing._
import java.awt.Dimension

object PTextAreaTest extends App {
  val frm = new JFrame()
  val pta = new PTextArea()

  pta.setPreferredSize((300, 300))

  frm.add(pta)
  frm.pack()
  frm.setVisible(true)

  pta.addCaretListener(new PCaretListener() {
    def caretMoved(textArea: PTextArea, selectionStart: Int, selectionEnd: Int) {
      val line = textArea.getLineOfOffset(selectionStart)
      val c = textArea.getLineContents(line);
      println(c)
    }
  })

}