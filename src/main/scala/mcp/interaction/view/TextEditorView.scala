package mcp.interaction.view

import scala.swing.EditorPane
import scala.swing.FlowPanel
import scala.swing.MainFrame
import scala.swing.SimpleSwingApplication
import scala.swing.Swing.pair2Dimension

object TextEditorView extends SimpleSwingApplication {

  def editor = new EditorPane

  def top = new MainFrame {
    title = "Greetings"

    val height = 100
    val width = 400

    preferredSize = (width, height)

    // TODO change layout - editor should fill the window
    contents = new FlowPanel {

      contents += editor

    }

    pack()

  }

  def create {
    val view = ContextAndFeedbackView.top
    view.pack()
    view.visible = true
  }
}