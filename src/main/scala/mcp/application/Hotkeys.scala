package mcp.application
import com.melloware.jintellitype.JIntellitypeConstants.MOD_WIN
import com.melloware.jintellitype.HotkeyListener
import com.melloware.jintellitype.JIntellitype
import mcp.interaction.view.ContextAndFeedbackView

object Hotkeys {
  //	java.awt.event.KeyEvent[KEY_PRESSED,keyCode=524,keyText=Windows,keyChar=Undefiniert keyChar,keyLocation=KEY_LOCATION_LEFT,rawCode=91,primaryLevelUnicode=0,scancode=91,extendedKeyCode=0x20c] on javax.swing.JRootPane[,0,0,0x0,layout=javax.swing.JRootPane$RootLayout,alignmentX=0.0,alignmentY=0.0,border=,flags=16777673,maximumSize=,minimumSize=,preferredSize=]
  // java.awt.event.KeyEvent[KEY_PRESSED,keyCode=65,keyText=A,keyChar='a',keyLocation=KEY_LOCATION_STANDARD,rawCode=65,primaryLevelUnicode=97,scancode=30,extendedKeyCode=0x41] on javax.swing.JRootPane[,0,0,0x0,layout=javax.swing.JRootPane$RootLayout,alignmentX=0.0,alignmentY=0.0,border=,flags=16777673,maximumSize=,minimumSize=,preferredSize=]

  override def finalize {
    JIntellitype.getInstance.cleanUp
    System exit 0
  }

  /**
   * define global hotkeys
   */
  def createGlobalHotkeys = {
    System.loadLibrary("JIntellitype")
    JIntellitype.getInstance().registerHotKey(1, MOD_WIN, 'A');

    JIntellitype.getInstance() addHotKeyListener new HotkeyListener {
      def onHotKey(id: Int) =
        id match {
          case 1 ⇒
            ContextAndFeedbackView.textField.requestFocus
            ContextAndFeedbackView.top.open
        }
    }
  }

}