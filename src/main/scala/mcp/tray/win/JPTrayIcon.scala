package mcp.tray.win
import java.awt.TrayIcon
import javax.swing.JDialog
import javax.swing.JPopupMenu
import java.awt.Frame
import java.awt.Image
import javax.swing.event.PopupMenuListener
import javax.swing.event.PopupMenuEvent
import java.awt.event.MouseEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseListener

class JPTrayIcon(image: Image) extends TrayIcon(image) {

  /**
   * @return the menu
   */
  class TrayMouseListener extends MouseListener {
    def showJPopupMenu(evt: MouseEvent) {
      if (evt.isPopupTrigger && menu != null) {
        DIALOG.setLocation(evt.getX, evt.getY - menu.getPreferredSize.height)
        DIALOG.setVisible(true)
        menu.show(DIALOG.getContentPane, 0, 0)
        // popup works only for focused windows
        DIALOG.toFront
      }
    }
    def mousePressed(evt: MouseEvent) = showJPopupMenu(evt)
    def mouseReleased(evt: MouseEvent) = showJPopupMenu(evt)
    def mouseClicked(evt: MouseEvent) = showJPopupMenu(evt)
    def mouseEntered(evt: MouseEvent) = Unit
    def mouseExited(evt: MouseEvent) = Unit
  }

  class TrayPopupListener extends PopupMenuListener {
    def popupMenuWillBecomeVisible(evt: PopupMenuEvent) = Unit
    def popupMenuWillBecomeInvisible(evt: PopupMenuEvent) = DIALOG.setVisible(false)
    def popupMenuCanceled(evt: PopupMenuEvent) = DIALOG.setVisible(false)
  }

  var menu: JPopupMenu = null
  val DIALOG = new JDialog()

  val popupListener = new TrayPopupListener
  val mouseLstener = new TrayMouseListener

  {
    DIALOG.setUndecorated(true)
    DIALOG.setAlwaysOnTop(true)
  }

  def getJPopupMenu: JPopupMenu = menu

  /**
   *
   * @param menu
   */
  def setJPopupMenu(menu: JPopupMenu) {
    if (this.menu != null) {
      this.menu.removePopupMenuListener(popupListener)
      removeMouseListener(mouseLstener)
    }
    if (menu != null) {
      this.menu = menu
      this.menu.addPopupMenuListener(popupListener)
      addMouseListener(mouseLstener)
    }
  }
}

