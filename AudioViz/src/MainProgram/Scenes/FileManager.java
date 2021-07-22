package MainProgram.Scenes;

import MainProgram.mySong;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;


/**
 * https://www.youtube.com/watch?v=SpL3EToqaXA&ab_channel=thenewboston
 */
public class FileManager {
    private static File newSongFile;
    private static mySong newSong;

    private static TextField songTitle;
    private static TextField songArtist;
    private static Label fileLocation;

    public static mySong display() {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add a Song.");

        window.setMinWidth(300);
        window.setMinHeight(250);

        // STYLING FOR ADDING A NEW FILE

        // Add row for label

        HBox firstRow = new HBox();

        Label addFile = new Label("Add New Song");
        String textCSS =
                "-fx-text-fill: #626868;" +
                "-fx-font-weight: bold;";
        addFile.setStyle(textCSS);

        firstRow.getChildren().add(addFile);
        firstRow.setPadding(new Insets(10, 10, 10, 10));

        // Add row for adding song title
        HBox secondRow = new HBox();
        songTitle = new TextField();
        String textBoxStyle =
                        "-fx-border-radius: 5px;" +
                        "-fx-border-color: #0B032D;" +
                        "-fx-background-color: #626868;" +
                        "-fx-background-radius: 5px;" +
                        "-fx-text-fill: #ECF0F1;" +
                        "-fx-font-weight: bold;" +
                        "-fx-prompt-text-fill: #ECF0F1;";
        songTitle.setStyle(textBoxStyle);
        songTitle.setMinWidth(200);
        songTitle.setPromptText("Enter Song Title");

        // Add nodes to first row
        secondRow.getChildren().addAll(songTitle);
        secondRow.setAlignment(Pos.TOP_LEFT);
        secondRow.setPadding(new Insets(10, 10, 10, 10));
        secondRow.setSpacing(20);

        // Add row for adding song artist

        HBox thirdRow = new HBox();
        songArtist = new TextField();
        songArtist.setStyle(textBoxStyle);
        songArtist.setMinWidth(200);
        songArtist.setPromptText("Enter Song Artist");

        thirdRow.getChildren().add(songArtist);
        thirdRow.setAlignment(Pos.TOP_LEFT);
        thirdRow.setPadding(new Insets(10, 10, 10, 10));
        thirdRow.setSpacing(20);

        // Add row for file

        HBox forthRow = new HBox();
        fileLocation = new Label();
        fileLocation.setStyle(textBoxStyle);
        fileLocation.setMinWidth(200);
        fileLocation.setText("Add File Location");

        Button getFile = new Button();
        getFile.setText("...");
        getFile.setOnAction(event -> {
            Stage primaryStage = (Stage)window.getScene().getWindow();
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(primaryStage);
            setFile(file);
        });

        forthRow.getChildren().addAll(fileLocation, getFile);
        forthRow.setAlignment(Pos.TOP_LEFT);
        forthRow.setPadding(new Insets(10, 10, 10, 10));
        forthRow.setSpacing(20);

        HBox fithRow = new HBox();

        HBox sixthRow = new HBox();
        sixthRow.setAlignment(Pos.BOTTOM_CENTER);
        sixthRow.setPadding(new Insets(10, 10, 10, 10));
        sixthRow.setSpacing(20);

        Button confirmSong = new Button();
        confirmSong.setText("Confirm");
        confirmSong.setOnAction(event -> {
            if(!songArtist.getText().isEmpty() || songArtist.getText() != null || !songTitle.getText().isEmpty() || songTitle.getText() != null || !fileLocation.getText().isEmpty() || fileLocation.getText() != null) {
                mySong newSong = new mySong(songTitle.getText(),songArtist.getText(), fileLocation.getText());
                setSong(newSong);
                window.close();
            } else {
                Label warning = new Label("All fields required to add Song.");
                String warningStyle =
                        "-fx-border-radius: 5px;" +
                                "-fx-border-color: #0B032D;" +
                                "-fx-background-color: #626868;" +
                                "-fx-background-radius: 5px;" +
                                "-fx-text-fill: #ECF0F1;" +
                                "-fx-font-weight: bold;" +
                                "-fx-prompt-text-fill: #DE1A1A;";
                warning.setStyle(warningStyle);
                sixthRow.getChildren().add(warning);
            }
        });

        fithRow.getChildren().add(confirmSong);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(firstRow, secondRow, thirdRow, forthRow, fithRow, sixthRow);
        layout.setAlignment(Pos.TOP_CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        return newSong;
    }

    private static void setFile(File file){
        fileLocation.setText(file.toString());
        newSongFile = file;
    }

    private static void setSong(mySong song) {
        newSong = song;
    }

}
