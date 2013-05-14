package mcp.tray.win

import java.awt.CheckboxMenuItem
import java.awt.SystemTray
import java.awt.TrayIcon
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.InputEvent
import java.awt.event.ItemEvent
import java.awt.event.ItemListener
import java.awt.event.KeyEvent
import java.io.File
import java.net.URL
import scala.swing.Action
import scala.swing.Image
import scala.swing.Menu
import scala.swing.MenuItem
import scala.swing.Separator
import javax.swing.ImageIcon
import javax.swing.JMenuItem
import javax.swing.JOptionPane
import javax.swing.KeyStroke
import mcp.scala.swing.PopupMenu

object TrayIntegration {

  /**
   * create system tray
   */
  def createSystemTray = {
    if (!SystemTray.isSupported)
      error("SystemTray is not supported")

    val popup = new PopupMenu
    val trayIcon = new JPTrayIcon(createImage(new File("""C:\Users\wissmann\workspaces\scala\de.j3w7.tnt\images\tray.gif""") toURL, "tray icon"))
    trayIcon.setImageAutoSize(true)

    val tray = SystemTray.getSystemTray()
    // Create a pop-up menu components
    val aboutItem = new MenuItem(new Action("About") {
      mnemonic = KeyEvent.VK_O
      accelerator = Some(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK))
      icon = new ImageIcon("build/icon.png")
      toolTip = "ファイルを開く"
      def apply() {}
    })
    val cb2 = new CheckboxMenuItem("Set tooltip")
    val displayMenu = new Menu("Display")
    val errorItem = new MenuItem("Error")
    val warningItem = new MenuItem("Warning")
    val infoItem = new MenuItem("Info")
    val noneItem = new MenuItem("None")
    val exitItem = new MenuItem("Exit")

    //Add components to pop-up menu
    popup.contents += aboutItem
    popup.contents += new Separator
    //TODO    popup.contents += cb2
    popup.contents += new Separator
    popup.contents += (displayMenu)
    displayMenu.contents += (errorItem)
    displayMenu.contents += (warningItem)
    displayMenu.contents += (infoItem)
    displayMenu.contents += (noneItem)
    popup.contents += (exitItem)

    trayIcon.setJPopupMenu(popup.peer)

    try {
      tray.add(trayIcon)
    } catch {
      case e ⇒
        System.out.println("TrayIcon could not be added.")
    }

    trayIcon addActionListener new ActionListener {
      def actionPerformed(e: ActionEvent) {
        JOptionPane.showMessageDialog(null,
          "This dialog box is run from System Tray")
      }
    }

    aboutItem.peer addActionListener new ActionListener {
      def actionPerformed(e: ActionEvent) {
        JOptionPane.showMessageDialog(null,
          "This dialog box is run from the About menu item")
      }
    }

    cb2.addItemListener(new ItemListener() {
      def itemStateChanged(e: ItemEvent) {
        val cb2Id = e.getStateChange()
        if (cb2Id == ItemEvent.SELECTED) {
          trayIcon.setToolTip("Sun TrayIcon")
        } else {
          trayIcon.setToolTip(null)
        }
      }
    })

    val listener = new ActionListener() {
      def actionPerformed(e: ActionEvent) {
        e.getSource.asInstanceOf[JMenuItem].getLabel match {
          case "Error" ⇒ trayIcon.displayMessage("Sun TrayIcon Demo", "This is an error message", TrayIcon.MessageType.ERROR)
          case "Warning" ⇒ trayIcon.displayMessage("Sun TrayIcon Demo", "This is a warning message", TrayIcon.MessageType.WARNING)
          case "Info" ⇒ trayIcon.displayMessage("Sun TrayIcon Demo", "This is an info message", TrayIcon.MessageType.INFO)
          case "None" ⇒ trayIcon.displayMessage("Sun TrayIcon Demo", "This is an ordinary message", TrayIcon.MessageType.NONE)
        }
      }
    }

    errorItem.peer.addActionListener(listener)
    warningItem.peer.addActionListener(listener)
    infoItem.peer.addActionListener(listener)
    noneItem.peer.addActionListener(listener)

    exitItem.peer.addActionListener(new ActionListener() {
      def actionPerformed(e: ActionEvent) {
        tray.remove(trayIcon)
        System.exit(0)
      }
    })
  }

  def createImage(imageURL: URL, description: String): Image = {
    if (imageURL == null) {
      System.err.println("Resource not found: " + imageURL)
      return null
    } else {
      return (new ImageIcon(imageURL, description)).getImage()
    }
  }
}
