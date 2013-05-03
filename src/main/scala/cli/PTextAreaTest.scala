package cli;
import e.ptextarea.PTextArea
import javax.swing.JFrame

object PTextAreaTest extends App {
  val frm = new JFrame()
  val pta = new PTextArea()
  
  frm.add(pta)
  frm.setVisible(true)
}