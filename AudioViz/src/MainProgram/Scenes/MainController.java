package MainProgram.Scenes;

import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Pane;

public class MainController {
    public Pane vistaHolder;
    public MenuBar mainMenu;

    public void setVista(Node node) {
        vistaHolder.getChildren().setAll(node);
    }
    /*

        vistaHolder.getChildren().setAll(FXMLLoader.load(SceneController.class.getResource(scenes[fxml]))

            mainController.setVista(FXMLLoader.load(SceneController.class.getResource(scenes[fxml])));
        } catch (IOException e) {
            e.printStackTrace();
        }

     */
}
