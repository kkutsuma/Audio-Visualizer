package MainProgram.Scenes;

import MainProgram.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Scene2Controller implements Initializable {

    SceneController sceneController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sceneController = SceneController.getInstance();
    }

    public void previousPane(ActionEvent actionEvent) {
        sceneController.loadScene(1);
    }


}
