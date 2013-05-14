package mcp.interaction.view

import java.awt.Color
import java.awt.GraphicsEnvironment
import scala.swing.Swing.pair2Dimension
import scala.swing.event.Key
import scala.swing.event.KeyPressed
import scala.swing.Dimension
import scala.swing.FlowPanel
import scala.swing.Label
import scala.swing.MainFrame
import scala.swing.SimpleSwingApplication
import scala.swing.TextField
import mcp.interaction.dnd.FileTransferHandler
import scala.swing.Panel
import javax.swing.JComponent
import scala.swing.Component

object ContextAndFeedbackView extends SimpleSwingApplication {

  val textField = new TextField {
    text = "Hello World!"
    peer.setTransferHandler(new FileTransferHandler())
    peer.setDragEnabled(true)
  }

  val dropArea = new Component {
    background = Color.blue.brighter()
    preferredSize = (100, 100)
   // size = (100, 100)
    // FIXME this seems to have no effect:
    //    peer.setDragEnabled(true)
    peer.setTransferHandler(new FileTransferHandler())
  }

  def top = new MainFrame {
    title = "Greetings"

    def screen = GraphicsEnvironment.getLocalGraphicsEnvironment.getDefaultScreenDevice.getDefaultConfiguration().getBounds
    val screen_bottom = screen.getMaxY.toInt
    val screen_middle = screen.getCenterY.toInt
    val height = 100
    val width = 400

    peer.setTransferHandler(new FileTransferHandler())

    preferredSize = (width, height)

    // FIXME this seems to have no effect:
    location.x = screen_middle - (width / 2)
    location.y = screen_bottom - height

    contents = new FlowPanel {

      contents += textField

      listenTo(textField.keys)
      reactions += {
        case KeyPressed(`textField`, Key.Enter, _, _) ⇒
          println("Ok, input " + textField.text) // TODO implement proper reaction
      }

      contents += dropArea

      listenTo(dropArea)
      reactions += { case event ⇒ println(event) }
    }
  }

  def create {
    val view = ContextAndFeedbackView.top
    view.pack()
    view.visible = true
  }
}