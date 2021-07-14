package MainProgram.Scenes;

import MainProgram.MyMediaPlayer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GraphController implements Initializable {

    @FXML
    public Rectangle b1;
    @FXML
    public Rectangle b2;
    @FXML
    public Rectangle b3;
    @FXML
    public Rectangle b4;
    @FXML
    public Rectangle b5;
    @FXML
    public Rectangle b6;
    @FXML
    public Rectangle b7;
    @FXML
    public Rectangle b8;
    @FXML
    public Rectangle b9;
    @FXML
    public Rectangle b10;
    public Rectangle[] graphBars;
    public HBox barHolder;
    private Timeline timeline;
    ArrayList<Rectangle> listOfBars;
    MediaPlayer player;
    float[] prevalues;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        player = MyMediaPlayer.getInstance().getPlayer();
        graphBars = new Rectangle[]{b1, b2, b3, b4, b5, b6, b7, b8, b9, b10};

        player.setAudioSpectrumNumBands(10);
        prevalues = new float[10];
        player.setAudioSpectrumInterval(0.01);
        player.setAudioSpectrumListener((a, b, c, d) -> {
            updateGraph(c, d);
        });
        listOfBars = new ArrayList<Rectangle>();
        listOfBars.add(new Rectangle(50, 300));
        listOfBars.get(0).setId("b11");
        barHolder.getChildren().add(listOfBars.get(0));
    }

    public void updateGraph(float [] c, float[] d) {
        // MediaPlayer player = MyMediaPlayer.getInstance().getPlayer();
        for(int i = 0; i < c.length; i++) {
            graphBars[i].setHeight((((c[i] + prevalues[i])/2) - player.getAudioSpectrumThreshold()) * 10);
            listOfBars.get(0).setHeight((c[0] - player.getAudioSpectrumThreshold()) * 10);
        }

        for(int i = 0; i < c.length; i++) {
            graphBars[i].setHeight((c[i] - player.getAudioSpectrumThreshold()) * 10);
            listOfBars.get(0).setHeight((c[0] - player.getAudioSpectrumThreshold()) * 10);
        }
        prevalues = c;
    }


}
