package fhj.swengb.assignments.ttt.clagger

import java.net.URL
import java.util.ResourceBundle
import javafx.event.EventHandler
import javafx.fxml.{FXML, Initializable, FXMLLoader}
import javafx.geometry.Pos
import javafx.scene.control.{Button, Label}
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
  @FXML var winner: Label = _
  @FXML var newGame : Button = _

  //start a new game
  var game = TicTacToe()
  val cellMap:Map[Int, TMove] =  Map(0 -> TopLeft, 1 -> TopCenter, 2 -> TopRight,
                  3 -> MiddleLeft, 4 -> MiddleCenter, 5 -> MiddleRight,
                  6 -> BottomLeft, 7 -> BottomCenter, 8 -> BottomRight)




  override def initialize(location: URL, resources: ResourceBundle): Unit = {
    initializeGame

  }

  def initializeGame() = {



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
      cell.setOnMouseClicked(mouseEventHandlerCellClick)
      cell.setText("")
      count +=1

    }
    // initialize grid
    grid.setPrefSize(250,250)
    grid.addRow(0, cell0, cell1, cell2)
    grid.addRow(1, cell3, cell4, cell5)
    grid.addRow(2, cell6, cell7, cell8)
    grid.setGridLinesVisible(true)


    newGame.setOnMouseClicked(mouseEventHandlerNewGame)

  }


  val mouseEventHandlerCellClick: EventHandler[_ >: MouseEvent] = new EventHandler[MouseEvent] {

    override def handle(event: MouseEvent): Unit = {
      event.getSource match {
        case onClick: Label => {

          // if cell is not set yet
          if(onClick.getText.equals("")) {

            // make an X or an O at the GridPane
            if(game.nextPlayer.equals(PlayerA))
              onClick.setText("O")
            else
              onClick.setText("X")

            // turn the move
            val position:Option[TMove] = cellMap.get(onClick.getUserData().toString.toInt)
            game = game.turn(position.get, game.nextPlayer)

            // if there is a winner or a draw
            if(game.gameOver)
              {
                grid.setDisable(true)

                if (game.winner.get._1.equals(noPlayer))
                  winner.setText("Draw!")
                if (game.winner.get._1.equals(PlayerA))
                  winner.setText("Player A!")
                if (game.winner.get._1.equals(PlayerB))
                  winner.setText("Player B!")
              }

          }

          else
            println("cell already set")
        }

        case _ => assert(false)
      }
    }
  }

  val mouseEventHandlerNewGame: EventHandler[_ >: MouseEvent] = new EventHandler[MouseEvent] {

    override def handle(event: MouseEvent): Unit = {
      event.getSource match {
        case onClick: Button => restartGame
        case _ => assert(false)
      }
    }
  }


  def restartGame: Unit = {
    val node = grid.getChildren().get(0) //has the gridlines in it
    game = TicTacToe()                   //start a new TicTacToe
    grid.getChildren().clear()
    grid.setDisable(false)
    grid.getChildren().add(0,node)  //add the gridlines again
    winner.setText("")
    initializeGame                  //initialize the new game

  }



}
