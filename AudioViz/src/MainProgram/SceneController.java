package MainProgram;

import javafx.scene.layout.AnchorPane;

public interface SceneController {
    public void start(Integer numBands, AnchorPane vizPane);
    public void end();
    public String getName();
    public void update(double timestamp, double duration, float[] magnitudes, float[] phases);
}
