package MainProgram;

import MainProgram.Scenes.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class SceneController {

    private static SceneController uniqueInstance;
    private MainController mainController;
    private String[] scenes;

    private SceneController() {
        this.scenes = new String[3];
    }

    public static SceneController getInstance() {
        if(uniqueInstance == null) {
            synchronized (MyMediaPlayer.class) {
                if(uniqueInstance == null){
                    uniqueInstance = new SceneController();
                }
            }
        }
        return uniqueInstance;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void loadScene(int fxml) {
        try {
            mainController.setVista(
                    FXMLLoader.load(
                            SceneController.class.getResource(
                                    scenes[fxml]
                            )
                    )
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setScene(String fxml,int pos) {
        scenes[pos] = fxml;
    }

    public String getScene(int pos) {
        return this.scenes[pos];
    }

}
