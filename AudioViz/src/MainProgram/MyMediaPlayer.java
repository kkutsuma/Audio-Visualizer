package MainProgram;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
    private final Integer[] bandsList = {1, 2, 4, 8, 16, 20, 40, 60, 100, 120, 140};
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        mediaPlayer.stop();
        mediaPlayer.seek(Duration.ZERO);
        timeSlider.setValue(0);
    }
    
    private void handleUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
        Duration ct = mediaPlayer.getCurrentTime();
        double ms = ct.toMillis();
 
        timeSlider.setValue(ms);
        
       currentScene.update(timestamp, duration, magnitudes, phases);
    }
    
    @FXML
    private void handleOpen(Event event) {
        Stage primaryStage = (Stage)vizPane.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            openMedia(file);
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
            
            currentScene.start(numBands, vizPane);
            mediaPlayer.play();
        }  
    }
}
