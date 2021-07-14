package MainProgram.Scenes;

import MainProgram.MyMediaPlayer;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class MediaBarController implements Initializable {
    public AnchorPane menubar1;
    public Slider volumeBar;
    public Slider timerBar;
    public Pane mediaBar;
    private Duration duration;
    MediaBarController uniqueInstance;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MediaPlayer player = MyMediaPlayer.getInstance().getPlayer();

        // https://docs.oracle.com/javase/8/javafx/media-tutorial/playercontrol.htm

        /*
        player.setAudioSpectrumNumBands(10);
        player.setAudioSpectrumListener((a, b, c, d) -> {
            for(int i = 0; i < c.length; i++) {
                System.out.print(c[i] - player.getAudioSpectrumThreshold() + " ");
            }
            System.out.println();
        });
        */

        /**
         * Updates timeBar value to the song's current place
         */
        player.currentTimeProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                duration = player.getMedia().getDuration();
                updateValues();
            }
        });

        /**
         * Allows for changing timeBar & volumeBar behaviors
         */
        timerBar.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (timerBar.isValueChanging()) {
                    // multiply duration by percentage calculated by slider position
                    player.seek(duration.multiply(timerBar.getValue() / 100.0));
                }
            }
        });

        volumeBar.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (volumeBar.isValueChanging()) {
                    player.setVolume(volumeBar.getValue() / 100.0);
                }
            }
        });

    }

    protected void updateValues() {
        MediaPlayer player = MyMediaPlayer.getInstance().getPlayer();
        if (timerBar != null && volumeBar != null) {
            Platform.runLater(new Runnable() {
                public void run() {
                    Duration currentTime = player.getCurrentTime();
                    timerBar.setDisable(duration.isUnknown());
                    if (!timerBar.isDisabled()
                            && duration.greaterThan(Duration.ZERO)
                            && !timerBar.isValueChanging()) {
                        timerBar.setValue(currentTime.divide(duration).toMillis() * 100.0);
                    }
                    if (!volumeBar.isValueChanging()) {
                        volumeBar.setValue((int) Math.round(player.getVolume() * 100));
                    }
                }
            });
        }
    }




}
