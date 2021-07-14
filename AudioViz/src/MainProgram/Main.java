package MainProgram;


import MainProgram.Scenes.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {


    SceneController sceneController;

    @Override
    public void start(Stage stage) throws Exception {
        this.sceneController = SceneLoader.load();

        stage.setTitle("Audio Visulizer");
        stage.setScene(
                createScene(
                        loadMainPane()
                )
        );
        stage.setResizable(false);
        stage.show();
    }

    private Pane loadMainPane() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Pane mainPane = (Pane) loader.load(
                getClass().getResourceAsStream(
                        sceneController.getScene(0)
                )
        );
        MainController mainController = loader.getController();
        sceneController.setMainController(mainController);
        sceneController.loadScene(1);

        return mainPane;
    }

    private Scene createScene(Pane mainPane) {
        Scene scene = new Scene(mainPane);
        /* ADD CSS
        scene.getStylesheets().setAll(
                getClass().getResource("something.css").toExternalForm()
        );
        */
        return scene;
    }
    public static void main(String[] args) { launch(args); }
}
