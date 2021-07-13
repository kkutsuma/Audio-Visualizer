package MainProgram;

public class SceneLoader {
    public static SceneController load() {
        SceneController myController = SceneController.getInstance();
        myController.setScene("Scenes/main.fxml", 0);
        myController.setScene("Scenes/scene1.fxml", 1);
        myController.setScene("Scenes/scene2.fxml", 2);

        return myController;
    }
}
