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

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
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
    private Text filePathText;
    
    @FXML
    private Text bandsText;
    
    @FXML
    private Text visualizerNameText;
    
    @FXML
    private Text errorText;
    
    @FXML
    private Menu visualizersMenu;
    
    @FXML
    private Menu bandsMenu;
    
    @FXML
    private Slider timeSlider;
    
    private Media media;
    private MediaPlayer mediaPlayer;
    
    private Integer numBands = 4;//40
    private final Double updateInterval = 0.05;
    
    private ArrayList<SceneController> scenes;
    private SceneController currentScene;
    private final Integer[] bandsList = {1, 2, 4, 8, 16, 20, 40, 60, 100, 128, 256};

    private List<String[]> Songs;
    private int currentSong;
    private int songCount;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Create List of All Songs
        // https://mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
        try {
            String dir = System.getProperty("user.dir");
            Songs = Files.lines(Paths.get(dir+"/src/MainProgram/Data/songs.csv"))
                    .map(line -> line.split(","))
                    .collect(Collectors.toList());
            currentSong = 0;
            songCount = Songs.size()-1;
        } catch (IOException e) {
            e.printStackTrace();
        }

        bandsText.setText(Integer.toString(numBands));
        
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
        visualizerNameText.setText(currentScene.getName());
        
        for (Integer bands : bandsList) {
            MenuItem menuItem = new MenuItem(Integer.toString(bands));
            menuItem.setUserData(bands);
            menuItem.setOnAction((ActionEvent event) -> {
                selectBands(event);
            });
            bandsMenu.getItems().add(menuItem);
        }
        openMedia(new File(Songs.get(0)[0]));
    }
    
    private void selectScene(ActionEvent event) {
        MenuItem menuItem = (MenuItem)event.getSource();
        SceneController scene = (SceneController)menuItem.getUserData();
        changeScene(scene);
    }
    
    private void selectBands(ActionEvent event) {
        MenuItem menuItem = (MenuItem)event.getSource();
        numBands = (Integer)menuItem.getUserData();
        if (currentScene != null) {
            currentScene.start(numBands, vizPane);
        }
        if (mediaPlayer != null) {
            mediaPlayer.setAudioSpectrumNumBands(numBands);
        }
        bandsText.setText(Integer.toString(numBands));
    }
    
    private void changeScene(SceneController scene) {
        if (currentScene != null) {
            currentScene.end();
        }
        currentScene = scene;
        currentScene.start(numBands, vizPane);
        visualizerNameText.setText(currentScene.getName());
    }
    
    private void openMedia(File file) {
        filePathText.setText("");
        errorText.setText("");
        if (mediaPlayer != null) {
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
           
            mediaPlayer.setAutoPlay(true);
            filePathText.setText(file.getPath());
        } catch (Exception ex) {
            errorText.setText(ex.toString());
            System.out.println(ex);
        }
    }
    // set timeSlider value
    private void handleReady() {
        Duration duration = mediaPlayer.getTotalDuration();
        currentScene.start(numBands, vizPane);
        timeSlider.setMin(0);
        timeSlider.setMax(duration.toMillis());
    }
    
    private void handleEndOfMedia() {
        if(currentSong == songCount) {
            currentSong = 0;
        } else {
            currentSong = currentSong + 1;
        }
        mediaPlayer.stop();
        mediaPlayer.seek(Duration.ZERO);
        timeSlider.setValue(0);
        openMedia(new File(Songs.get(currentSong)[0]));
    }
    
    private void handleUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
        Duration ct = mediaPlayer.getCurrentTime();
        double ms = ct.toMillis();
 
        timeSlider.setValue(ms);
        
       currentScene.update(timestamp, duration, magnitudes, phases);
    }

    // https://stackoverflow.com/questions/1625234/how-to-append-text-to-an-existing-file-in-java
    @FXML
    private void handleOpen(Event event) {
        String dir = System.getProperty("user.dir");
        File newSong = FileManager.display();
        boolean exists = false;

        if(newSong != null) {
            System.out.println(newSong);
            for(String[] song : Songs) {
                if(song[0].equals(newSong.toString())) {
                    exists = true;
                    break;
                }
            }
            if(!exists) {
                try {
                    // Write to file
                    FileWriter myWriter = new FileWriter(dir+"/src/MainProgram/Data/songs.csv", true);
                    myWriter.write("\n" + newSong.toString());
                    myWriter.close();
                    // Update current song list
                    Songs.add(newSong.toString().split(","));
                    songCount = songCount + 1;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Song already exists!");
            }
        }
    }
    
    @FXML
    private void handlePlay(ActionEvent event) {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
        
    }
    
    @FXML
    private void handlePause(ActionEvent event) {
        if (mediaPlayer != null) {
           mediaPlayer.pause(); 
        }
    }
    
    @FXML
    private void handleStop(ActionEvent event) {
        if (mediaPlayer != null) {
           mediaPlayer.stop(); 
        }
        
    }
    
    @FXML
    private void handleSliderMousePressed(Event event) {
        if (mediaPlayer != null) {
           mediaPlayer.pause(); 
        }  
    }
    
    @FXML
    private void handleSliderMouseReleased(Event event) {
        if (mediaPlayer != null) {
            mediaPlayer.seek(new Duration(timeSlider.getValue()));
            //currentScene.end();
            //currentScene.start(numBands, vizPane);
            mediaPlayer.play();
        }  
    }
}
