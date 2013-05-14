package mcp.interaction.view

import java.awt.Dimension

import org.apache.commons.collections15.Factory

import edu.uci.ics.jung.algorithms.layout.StaticLayout
import edu.uci.ics.jung.graph.SparseMultigraph
import edu.uci.ics.jung.graph.Graph
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse
import edu.uci.ics.jung.visualization.control.ModalGraphMouse
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller
import edu.uci.ics.jung.visualization.VisualizationViewer
import javax.swing.JFrame
import javax.swing.JMenuBar

class EditingGraphViewer1 {

  val g: Graph[Integer, String] = new SparseMultigraph[Integer, String]

  val vertexFactory = new Factory[Integer] {
    var nodeCount = 0
    def create: Integer = {
      nodeCount += 1
      return nodeCount
    }
  }
  val edgeFactory = new Factory[String] {
    var edgeCount = 0
    def create: String = {
      edgeCount += 1
      return "E" + edgeCount
    }
  }
}

object EditingGraphViewer1 {

  def main(args: Array[String]) {

    val sgv = new EditingGraphViewer1
    val layout = new StaticLayout(sgv.g)
    layout.setSize(new Dimension(300, 300))

    val vv = new VisualizationViewer[Integer, String](layout)
    vv.setPreferredSize(new Dimension(350, 350))

    // Show vertex and edge labels
    vv.getRenderContext.setVertexLabelTransformer(new ToStringLabeller)
    vv.getRenderContext.setEdgeLabelTransformer(new ToStringLabeller)

    // Create a graph mouse and add it to the visualization viewer
    val gm = new EditingModalGraphMouse(vv.getRenderContext,
      sgv.vertexFactory, sgv.edgeFactory)
    vv.setGraphMouse(gm)
    val frame = new JFrame("Editing Graph Viewer 1")
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    frame.getContentPane.add(vv)
    // Let's add a menu for changing mouse modes
    val menuBar = new JMenuBar
    val modeMenu = gm.getModeMenu // Obtain mode menu from the mouse
    modeMenu.setText("Mouse Mode")
    modeMenu.setIcon(null) // I'm using this in a main menu
    modeMenu.setPreferredSize(new Dimension(80, 20)) // Change the size
    menuBar.add(modeMenu)
    frame.setJMenuBar(menuBar)
    gm.setMode(ModalGraphMouse.Mode.EDITING) // Start off in editing mode
    frame.pack
    frame.setVisible(true)

  }

}
