package mcp.application

import mcp.tray.win.TrayIntegration
import com.melloware.jintellitype.JIntellitype
import javax.swing.SwingUtilities
import mcp.interaction.view.ContextAndFeedbackView
import javax.swing.JFrame

object Main {

  def main(args: Array[String]) {
    SwingUtilities invokeLater new Runnable {
      def run = createAndShowGUI
    }
  }

  def createAndShowGUI {
    TrayIntegration.createSystemTray

    //    JFrame.setDefaultLookAndFeelDecorated(true)
    //    ContextAndFeedbackView.top.peer.setUndecorated(true)
    //    ContextAndFeedbackView.top.peer.setLocationByPlatform(true)
    //    ContextAndFeedbackView.top.peer.setAlwaysOnTop(true)
    ContextAndFeedbackView.create
    Hotkeys.createGlobalHotkeys
  }

}
