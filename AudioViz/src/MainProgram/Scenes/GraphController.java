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
    @FXML
    public Rectangle b11;
    @FXML
    public Rectangle b12;
    @FXML
    public Rectangle b13;
    @FXML
    public Rectangle b14;
    @FXML
    public Rectangle b15;
    @FXML
    public Rectangle b16;
    @FXML
    public Rectangle b17;
    @FXML
    public Rectangle b18;
    @FXML
    public Rectangle b19;
    @FXML
    public Rectangle b20;
    @FXML
    public Rectangle b21;
    @FXML
    public Rectangle b22;
    @FXML
    public Rectangle b23;
    @FXML
    public Rectangle b24;
    @FXML
    public Rectangle b25;
    public Rectangle[] graphBars;
    public HBox barHolder;
    private Timeline timeline;
    ArrayList<Rectangle> listOfBars;
    private MediaPlayer player;
    float[] prevalues;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        player = MyMediaPlayer.getInstance().getPlayer();
        graphBars = new Rectangle[]{b1, b2, b3, b4, b5, b6, b7, b8, b9, b10,b11, b12, b13, b14, b15, b16, b17, b18, b19, b20, b21, b22, b23, b24, b25};
        player.setAudioSpectrumNumBands(25);
        prevalues = new float[25];
        player.setAudioSpectrumInterval(0.01);
        player.setAudioSpectrumListener((a, b, c, d) -> { // (current time, durration, []magnitudes, []phases)
            updateGraph(c, d);
        });
/*        listOfBars = new ArrayList<Rectangle>();
        listOfBars.add(new Rectangle(50, 300));
        listOfBars.get(0).setId("b11");
        barHolder.getChildren().add(listOfBars.get(0));*/
    }

    private void updateGraph(float [] c, float[] d) {
        // MediaPlayer player = MyMediaPlayer.getInstance().getPlayer();
        for(int i = 0; i < c.length; i++) {
            graphBars[i].setHeight((((c[i] + prevalues[i])/2) - player.getAudioSpectrumThreshold()) * 10);
            // listOfBars.get(0).setHeight((c[0] - player.getAudioSpectrumThreshold()) * 10);
        }

        for(int i = 0; i < c.length; i++) {
            graphBars[i].setHeight((c[i] - player.getAudioSpectrumThreshold()) * 10);
            // listOfBars.get(0).setHeight((c[0] - player.getAudioSpectrumThreshold()) * 10);
        }
        prevalues = c;
    }


}
