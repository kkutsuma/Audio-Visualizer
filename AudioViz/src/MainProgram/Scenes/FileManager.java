package MainProgram.Scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;


/**
 * https://www.youtube.com/watch?v=SpL3EToqaXA&ab_channel=thenewboston
 */
public class FileManager {
    static File newSong;

    public static File display() {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add a Song.");

        window.setMinWidth(300);
        window.setMinHeight(250);

        // STYLING FOR ADDING A NEW FILE

        // Add row for adding a new file
        HBox firstRow = new HBox();
        Label fileLocation = new Label();
        String fileLocationStyle =
                        "-fx-border-radius: 50px;" +
                        "-fx-border-color: black;" +
                        "-fx-border-color: black;" +
                        "-fx-background-color: gainsboro;" +
                        "-fx-background-radius: 50px;";
        fileLocation.setStyle(fileLocationStyle);
        fileLocation.setMinWidth(200);
        fileLocation.setWrapText(true);
        fileLocation.setText("\tFile LocationFile LocationFile LocationFile LocationFile Location");

        Button getFile = new Button();
        getFile.setText("...");
        getFile.setOnAction(event -> {
            Stage primaryStage = (Stage)window.getScene().getWindow();
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(primaryStage);
            setFile(file);
            window.close();
        });

        // Add nodes to first row
        firstRow.getChildren().addAll(fileLocation, getFile);
        firstRow.setAlignment(Pos.TOP_LEFT);
        firstRow.setPadding(new Insets(10, 10, 10, 10));

        VBox layout = new VBox(10);
        layout.getChildren().addAll(firstRow);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        return newSong;
    }

    static void setFile(File file){ newSong = file;}

}
