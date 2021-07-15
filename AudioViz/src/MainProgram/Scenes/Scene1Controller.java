package MainProgram.Scenes;

import MainProgram.MyMediaPlayer;
import MainProgram.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;


import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

public class Scene1Controller implements Initializable {
    public AnchorPane vista1;
    public Button leftButton;
    public Button pause_resume_button;
    @FXML
    public Pane MediaBar;
    public AnchorPane graphPane;
    private MyMediaPlayer customPlayer;
    private MediaPlayer player;
    private SceneController sceneController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.customPlayer = MyMediaPlayer.getInstance();
        this.sceneController = SceneController.getInstance();
        this.player = customPlayer.getPlayer();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("mediaBar.fxml"));
            MediaBar.getChildren().setAll(root);

            root = FXMLLoader.load(getClass().getResource("graph.fxml"));
            graphPane.getChildren().setAll(root);
            System.out.println("I loaded mediaBar.fxml &  graph.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void leftButtonCheck(ActionEvent actionEvent) {
        System.out.print("This is " + leftButton.getText());
    }

    public void pauseButtonClick(ActionEvent actionEvent) {
        MediaPlayer.Status currentStatus = player.getStatus();
        if(currentStatus == MediaPlayer.Status.PLAYING) {
            player.pause();
            pause_resume_button.setText("Resume");
        } else if (currentStatus == MediaPlayer.Status.PAUSED || currentStatus == MediaPlayer.Status.HALTED || currentStatus == MediaPlayer.Status.STOPPED) {
            player.play();
            pause_resume_button.setText("Pause");
        }
    }

    public void nextPane(ActionEvent actionEvent) {
        this.sceneController.loadScene(2);
    }

}
