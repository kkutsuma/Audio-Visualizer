package MainProgram;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import MainProgram.Scenes.*;

/**
 * FXML Controller class
 * http://stackoverflow.com/questions/11994366/how-to-reference-primarystage
 */
public class MyMediaPlayer implements Initializable {
    
    @FXML
    private AnchorPane vizPane;
    
    @FXML
    private MediaView mediaView;
    
    @FXML
    private Label songTitle;

    @FXML
    private Label songArtist;
    
    @FXML
    private Text errorText;
    
    @FXML
    private Menu visualizersMenu;
    
    @FXML
    private Menu bandsMenu;
    
    @FXML
    private Slider timeSlider;

    @FXML
    private Slider volumeSlider;

    @FXML
    private Button pauseResumeButton;
    
    private Media media;
    private MediaPlayer mediaPlayer;
    
    private Integer numBands = 128; //2^(8-1)
    private final Double updateInterval = 0.05;
    
    private ArrayList<SceneController> scenes;
    private SceneController currentScene;
    private final Integer[] bandsList = {1, 2, 4, 8, 16, 32, 64, 128, 256, 384, 512};

    private List<mySong> Songs;
    private int currentSong;
    private int songCount;

    private double vizPaneWidth;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Create List of All Songs
        // https://mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
        try {
            String dir = System.getProperty("user.dir");
            Songs = Files.lines(Paths.get(dir+"/src/MainProgram/Data/songs.csv"))
                    .map(line -> line.split(","))
                    .map(mySong::new)
                    .collect(Collectors.toList());
            currentSong = 0;
            songCount = Songs.size()-1;
            if(songCount <= 0) {
                songCount = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create List of the different Scenes & set current scene
        scenes = new ArrayList<>();
        scenes.add(new Scene1Controller());
        scenes.add(new Scene2Controller());
        for (SceneController scene : scenes) {
            MenuItem menuItem = new MenuItem(scene.getName());
            menuItem.setUserData(scene);
            menuItem.setOnAction((ActionEvent event) -> {
                selectScene(event);
            });
            visualizersMenu.getItems().add(menuItem);
        }
        currentScene = scenes.get(0);

        // Sets volume bar to change volume when bar value is changed
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (volumeSlider.isValueChanging()) {
                    mediaPlayer.setVolume(volumeSlider.getValue() / 100.0);
                }
            }
        });

        // Set Number of Bands to vizualize
        for (Integer bands : bandsList) {
            MenuItem menuItem = new MenuItem(Integer.toString(bands));
            menuItem.setUserData(bands);
            menuItem.setOnAction((ActionEvent event) -> {
                selectBands(event);
            });
            bandsMenu.getItems().add(menuItem);
        }

        // Start the first song
        if(!Songs.isEmpty()) {
            openMedia(Songs.get(0));
        }
    }

    /**
     * Changes the visualizer when option is changed in the menu
     * @param event Takes user input of a menu scene option
     */
    private void selectScene(ActionEvent event) {
        MenuItem menuItem = (MenuItem)event.getSource();
        SceneController scene = (SceneController)menuItem.getUserData();
        changeScene(scene);
    }

    /**
     * Changes the amount of bars the visualizer has when option is changed in the menu
     * @param event Takes user input of a menu band option
     */
    private void selectBands(ActionEvent event) {
        MenuItem menuItem = (MenuItem)event.getSource();
        numBands = (Integer)menuItem.getUserData();
        if (currentScene != null) {
            currentScene.start(numBands, vizPane);
        }
        if (mediaPlayer != null) {
            mediaPlayer.setAudioSpectrumNumBands(numBands);
        }
        // bandsText.setText(Integer.toString(numBands));
    }

    /**
     * Takes a SceneController and sets the current visualizer type to it
     *
     * @param scene a SceneController that the visualizer will  change to
     */
    private void changeScene(SceneController scene) {
        if (currentScene != null) {
            currentScene.end();
        }
        currentScene = scene;
        currentScene.start(numBands, vizPane);
    }

    /**
     * openMedia will set the song title, artist, and set the new song to be played
     *
     * @param song new Song to be played
     *
     */
    private void openMedia(mySong song) {
        double prevVolume = 1.0;
        File file = new File(song.getURL());
        songTitle.setText("");
        errorText.setText("");
        if (mediaPlayer != null) {
            prevVolume = mediaPlayer.getVolume();
            mediaPlayer.dispose();
        }
        
        try {
            media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.setOnReady(() -> {
                handleReady();
            });
            mediaPlayer.setOnEndOfMedia(() -> {
              handleEndOfMedia();
            });
            mediaPlayer.setAudioSpectrumNumBands(numBands);
            mediaPlayer.setAudioSpectrumInterval(updateInterval);
            
            mediaPlayer.setAudioSpectrumListener((double timestamp, double duration, float[] magnitudes, float[] phases) -> {
                handleUpdate(timestamp, duration, magnitudes, phases);
            });
            // Set up vizualization resizing
            vizPaneWidth = vizPane.getWidth();
            vizPane.widthProperty().addListener((obs, oldVal, newVal) -> {
                if(Math.abs(newVal.intValue() -  vizPaneWidth) > 10) {
                    handleWidthChange();
                    vizPaneWidth = vizPane.getWidth();
                }
            });

            // Set Values for UI (volume, song title/artist)
            mediaPlayer.setVolume(prevVolume);

            songTitle.setText(song.getTitle());
            songArtist.setText(song.getArtist());
            mediaPlayer.setAutoPlay(true);

        } catch (Exception ex) {
            errorText.setText(ex.toString());
            System.out.println(ex);
        }
    }

    /**
     * set duration and  timeSlider values
     */
    private void handleReady() {
        Duration duration = mediaPlayer.getTotalDuration();
        currentScene.start(numBands, vizPane);
        timeSlider.setMin(0);
        timeSlider.setMax(duration.toMillis());
    }

    /**
     * Sets action when for when the song ends
     */
    private void handleEndOfMedia() {
        if(currentSong == songCount) {
            currentSong = 0;
        } else {
            currentSong = currentSong + 1;
        }
        mediaPlayer.stop();
        mediaPlayer.seek(Duration.ZERO);
        timeSlider.setValue(0);
        openMedia(Songs.get(currentSong));
    }

    /**
     * Handles all values that need to be changed when a new sound is played
     * @param timestamp     current time of song
     * @param duration      durration of song
     * @param magnitudes    array of magnitudes of each band (band amt. is variable)
     * @param phases        array of phases of each band (band amt. is variable)
     */
    private void handleUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
        Duration ct = mediaPlayer.getCurrentTime();
        double ms = ct.toMillis();
 
        timeSlider.setValue(ms);

        if(!volumeSlider.isValueChanging()) {
            volumeSlider.setValue((int) Math.round(mediaPlayer.getVolume() * 100));
        }
        
       currentScene.update(timestamp, duration, magnitudes, phases);
    }

    /**
     * Updates visualizer bars when width changes
     */
    private void handleWidthChange() {
        if(mediaPlayer != null) {
            if(mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) {
                currentScene.start(numBands, vizPane);
            }
        }
    }

    /**
     * Opens new window for the user to add a new song
     * // https://stackoverflow.com/questions/1625234/how-to-append-text-to-an-existing-file-in-java
     *
     * @param event JavaFX event of clicking the menu button
     */
    @FXML
    private void handleOpen(Event event) {
        String dir = System.getProperty("user.dir");
        mySong newSong = FileManager.display();
        boolean exists = false;

        // Ensures only new songs are added by URL
        if(newSong != null) {
            for(mySong song : Songs) {
                if(song.getURL().equals(newSong.getURL())) {
                    exists = true;
                    break;
                }
            }
            if(!exists) {
                try {
                    // Write to file
                    FileWriter myWriter = new FileWriter(dir+"/src/MainProgram/Data/songs.csv", true);
                    if(songCount > 1) {
                        myWriter.write("\n" + newSong.getTitle() + "," + newSong.getArtist() + "," + newSong.getURL());
                        myWriter.close();
                        // Update current song list
                        Songs.add(newSong);
                        songCount = songCount + 1;
                    } else {
                        myWriter.write(newSong.getTitle() + "," + newSong.getArtist() + "," + newSong.getURL());
                        myWriter.close();
                        // Update current song list
                        Songs.add(newSong);
                        songCount = 0;
                        currentSong = 0;
                        openMedia(Songs.get(0));
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Song already exists!");
            }
        }
    }

    /**
     * If there is a song, sets the next song to be the previous song in the queue
     * @param event When user clicks the prev button
     */
    @FXML
    private void handlePrev(ActionEvent event) {
        if (mediaPlayer != null) {
            if(currentSong <= 1) {
                currentSong = songCount;
            } else {
                currentSong = currentSong - 1;
            }
            mediaPlayer.stop();
            mediaPlayer.seek(Duration.ZERO);
            timeSlider.setValue(0);
            openMedia(Songs.get(currentSong));
            pauseResumeButton.setText("Pause");
        }

    }

    /**
     * Pauses or Resumes music depending on if music is playing or paused.
     * @param event When user clicks the pause button
     */
    @FXML
    private void handlePause(ActionEvent event) {
        if (mediaPlayer != null) {
            MediaPlayer.Status currentStatus = mediaPlayer.getStatus();
            if(currentStatus.equals(MediaPlayer.Status.PLAYING)) {
                mediaPlayer.pause();
                pauseResumeButton.setText("Play");
            } else if (currentStatus.equals(MediaPlayer.Status.PAUSED) || currentStatus.equals(MediaPlayer.Status.STOPPED)) {
                mediaPlayer.play();
                pauseResumeButton.setText("Pause");
            }
            pauseResumeButton.setStyle("-fx-background-color: #626868");
        }
    }

    /**
     * If there is a song, sets the next song to be the next song in the queue
     * @param event When user clicks the next button
     */
    @FXML
    private void handleNext(ActionEvent event) {
        if(currentSong == songCount) {
            currentSong = 0;
        } else {
            currentSong = currentSong + 1;
        }
        mediaPlayer.stop();
        mediaPlayer.seek(Duration.ZERO);
        timeSlider.setValue(0);
        openMedia(Songs.get(currentSong));
        pauseResumeButton.setText("Pause");
    }

    /**
     * Pause song when user holds click on timer bar
     * @param event User click and holding on timer bar.
     */
    @FXML
    private void handleSliderMousePressed(Event event) {
        if (mediaPlayer != null) {
           mediaPlayer.pause();
        }  
    }

    /**
     * Moves the songs to the point where the user changed it in the timer bar
     * @param event User changes value of timer bar
     */
    @FXML
    private void handleSliderMouseReleased(Event event) {
        if (mediaPlayer != null) {
            mediaPlayer.seek(new Duration(timeSlider.getValue()));
            mediaPlayer.play();
            pauseResumeButton.setText("Pause");
        }  
    }
}
