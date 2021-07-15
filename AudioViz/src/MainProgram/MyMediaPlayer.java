package MainProgram;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.nio.file.Paths;

public class MyMediaPlayer {
    private volatile static MyMediaPlayer uniqueInstance;
    private Media music;
    private static MediaPlayer player;
    private boolean playStatus;

    private MyMediaPlayer() {
        music = new Media(Paths.get("src/Songs/wishYouWereHere.mp3").toUri().toString());
        player = new MediaPlayer(music);

        // set media player default settings
        player.setAutoPlay(true);
        this.playStatus = true;

        player.setVolume(0.1);
    }

    public static MyMediaPlayer getInstance() {
        if(uniqueInstance == null) {
            synchronized (MyMediaPlayer.class) {
                if(uniqueInstance == null){
                    uniqueInstance = new MyMediaPlayer();
                }
            }
        }
        return uniqueInstance;
    }

    public MediaPlayer getPlayer() {
        return this.player;
    }

}

