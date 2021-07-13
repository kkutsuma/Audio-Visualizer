package MainProgram;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;

public class MyMediaPlayer {
    private volatile static MyMediaPlayer uniqueInstance;
    private Media music;
    private MediaPlayer player;
    private boolean playStatus;

    private MyMediaPlayer() {
        music = new Media(Paths.get("src/Songs/wishYouWereHere.mp3").toUri().toString());
        this.player = new MediaPlayer(music);

        // set media player default settings
        this.player.setAutoPlay(true);
        this.playStatus = true;

        this.player.setVolume(0.1);
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

    public boolean getPlayStatus() {
        return this.playStatus;
    }

    public void play() {
        this.player.play();
        this.playStatus = true;
    }

    public void pause() {
        this.player.pause();
        this.playStatus = false;
    }

    public void playOrPause() {
        if(this.playStatus) {
            this.player.pause();
            this.playStatus = false;
        } else {
            this.player.play();
            this.playStatus = true;
        }
    }

    public void stop() {
        this.player.stop();
        this.playStatus = false;
    }

    /**
     * Increases media volume by 0.5
     */
    public void increaseVolume() {
        double currentVolume = this.player.getVolume();
        double increment = 0.5;
        if(currentVolume >= (1.0 - increment)) {
            this.player.setVolume(1.0);
        } else {
            this.player.setVolume(currentVolume + increment);
        }
    }

    /**
     * Decrease media volume by 0.5
     */
    public void decreaseVolume() {
        double currentVolume = this.player.getVolume();
        double increment = 0.5;
        if(currentVolume <= (0 + increment)) {
            this.player.setVolume(0);
        } else {
            this.player.setVolume(currentVolume - increment);
        }
    }

}
