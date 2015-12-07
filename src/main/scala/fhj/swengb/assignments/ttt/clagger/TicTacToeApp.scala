package fhj.swengb.assignments.ttt.clagger

import java.net.URL
import java.util.ResourceBundle
import javafx.fxml.{Initializable, FXMLLoader}
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
  override def initialize(location: URL, resources: ResourceBundle): Unit = ???
}
