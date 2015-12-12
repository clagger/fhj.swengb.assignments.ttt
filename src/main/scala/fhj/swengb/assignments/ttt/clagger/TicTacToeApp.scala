package fhj.swengb.assignments.ttt.clagger

import java.net.URL
import java.util.ResourceBundle
import javafx.event.EventHandler
import javafx.fxml.{FXML, Initializable, FXMLLoader}
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.input.MouseEvent
import javafx.scene.layout.{GridPane, AnchorPane, BorderPane}
import javafx.scene.text.Font
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

import scala.util.control.NonFatal

class TicTacToeApp extends javafx.application.Application {

  val Fxml = "./TicTacToeApp.fxml"
  //val Css = "fhj/swengb/avatarix/Gruppe2Avatarix.css"

  val loader = new FXMLLoader(getClass.getResource(Fxml))

  override def start(stage: Stage): Unit =
    try {

      loader.load[Parent]() // side effect
      val scene = new Scene(loader.getRoot[Parent])
      stage.setScene(scene)
      //stage.getScene.getStylesheets.add(Css)
      stage.show()
    } catch {
      case NonFatal(e) => e.printStackTrace()
    }

}

class TicTacToeController extends Initializable {

  @FXML var anchor_pane: AnchorPane = _
  @FXML var grid: GridPane = _

  //start a new game
  val game = TicTacToe()
  val cellMap:Map[Int, TMove] =  Map(0 -> TopLeft, 1 -> TopCenter, 2 -> TopRight,
                  3 -> MiddleLeft, 4 -> MiddleCenter, 5 -> MiddleRight,
                  6 -> BottomLeft, 7 -> BottomCenter, 8 -> BottomRight)




  override def initialize(location: URL, resources: ResourceBundle): Unit = {

    //initalizing GridPane and it's cells

    val cell0 = new Label()
    val cell1 = new Label()
    val cell2 = new Label()
    val cell3 = new Label()
    val cell4 = new Label()
    val cell5 = new Label()
    val cell6 = new Label()
    val cell7 = new Label()
    val cell8 = new Label()

    val cells = List(cell0, cell1, cell2, cell3,cell4, cell5, cell6, cell7, cell8)

    var count = 0
    for(cell <- cells){
      cell.setMaxSize(250,250)
      cell.setUserData(count)
      cell.setAlignment(Pos.CENTER)
      cell.setFont(new Font("Arial", 30))
      cell.setOnMouseClicked(mouseEventHandler)
      count +=1

    }

    grid.addRow(0, cell0, cell1, cell2)
    grid.addRow(1, cell3, cell4, cell5)
    grid.addRow(2, cell6, cell7, cell8)
    grid.setPrefSize(250,250)





  }


  val mouseEventHandler: EventHandler[_ >: MouseEvent] = new EventHandler[MouseEvent] {

    override def handle(event: MouseEvent): Unit = {
      event.getSource match {
        case onClick: Label => {
          if(game.nextPlayer.equals(PlayerA))
            onClick.setText("X")
          else
            onClick.setText("O")

        val position:Option[TMove] = cellMap.get(onClick.getUserData().toString.toInt)
          game.turn(position.get,game.nextPlayer)



          //to be continued ...





        }

        case _ => assert(false)
      }
    }
  }


}
