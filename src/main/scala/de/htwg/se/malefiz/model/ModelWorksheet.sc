





import scala.swing.{Action, BorderPanel, Button, Dimension, Frame, GridPanel, Menu, MenuBar, MenuItem, Orientation, SplitPane}
import BorderPanel.Position._

class EntryGui extends Frame {

  title = "Wilkommen zu Malefiz"

  menuBar = new MenuBar{
    contents += new Menu("Malefiz") {
      contents += new MenuItem(Action("Quit") {
        //System.exit(0)
      })
    }
  }

  contents = new GridPanel(2, 1) {
    contents += new BorderPanel {
      layout += new Button("New Game") -> Center
      layout += new Button("Quit") -> South
    }
  }




  size = new Dimension(200, 300)


}