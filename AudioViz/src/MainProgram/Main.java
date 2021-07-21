package MainProgram;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage mainStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MediaPlayer.fxml"));
        
        Scene scene = new Scene(root);
        
        mainStage.setScene(scene);
        mainStage.show();
        
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
