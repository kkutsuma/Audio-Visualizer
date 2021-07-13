package MainProgram.Scenes;

import MainProgram.MyMediaPlayer;
import MainProgram.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;


import java.net.URL;
import java.util.ResourceBundle;

public class Scene1Controller implements Initializable {
    public AnchorPane vista1;
    public Button leftButton;
    public Button pause_resume_button;
    private MyMediaPlayer player;
    private SceneController sceneController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.player = MyMediaPlayer.getInstance();
        this.sceneController = SceneController.getInstance();
    }

    public void leftButtonCheck(ActionEvent actionEvent) {
        System.out.print("This is " + leftButton.getText());
    }

    public void pauseButtonClick(ActionEvent actionEvent) {
        if(!player.getPlayStatus()) {
            player.play();
            pause_resume_button.setText("Pause");
        } else {
            player.pause();
            pause_resume_button.setText("Resume");
        }
    }

    public void nextPane(ActionEvent actionEvent) {
        this.sceneController.loadScene(2);
    }
}
