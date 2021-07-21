package MainProgram.Scenes;

import MainProgram.SceneController;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import static java.lang.Integer.min;

/**
 * Linear Gradient: https://www.javatpoint.com/javafx-gradient-color
 */
public class Scene1Controller implements SceneController {
    private final String name = "Scene 1";

    private Integer numBands;
    private AnchorPane vizPane;
    
    private final Double bandHeightPercentage = 1.3;
    private final Double minWidth = 10.0;
    
    private Double width = 0.0;
    private Double height = 0.0;
    
    private Double bandWidth = 0.0;
    private Double bandHeight = 0.0;
    private Double halfBandHeight = 0.0;
    
    //private final Double startHue = 260.0;
    
    public Rectangle[] graphBars;

    public Scene1Controller(){

    }

    @Override
    public void start(Integer numBands, AnchorPane vizPane) {
        end();

        this.numBands = numBands;
        this.vizPane = vizPane;

        height = vizPane.getHeight();
        width = vizPane.getWidth();
        bandWidth = width / (numBands);
        bandHeight = height * bandHeightPercentage;
        halfBandHeight = bandHeight / 2;
        graphBars = new Rectangle[numBands];

        for(int i = 0; i < numBands; ++i){
            Rectangle bar = new Rectangle();
            bar.setY(height / 3);
            bar.setWidth(bandWidth);
            bar.setHeight(minWidth);
            bar.setX(bandWidth * i);
            bar.setX( bandWidth * i);
            bar.setFill(Color.DODGERBLUE);
            vizPane.getChildren().add(bar);
            graphBars[i] = bar;
        }

    }

    @Override
    public void end() {
        if (graphBars != null) {
            for (Rectangle rectangle : graphBars) {
                vizPane.getChildren().remove(rectangle);
            }
           graphBars = null;
       }
    }

    @Override
    public String getName() {

        return name;
    }

    @Override
    public void update(double timestamp, double duration, float[] magnitudes, float[] phases) {
        if (graphBars == null) {
            return;
        }
        
        Integer num = min(graphBars.length, magnitudes.length);
        
        for (int i = 0; i < num; i++) {
            // System.out.println(magnitudes[i]);
            
            graphBars[i].setScaleY(2);
            graphBars[i].setHeight(((60.0 + magnitudes[i])/60) * halfBandHeight + minWidth);
            Stop[] stops = new Stop[] { new Stop(0, Color.LIGHTBLUE), new Stop(1, Color.BLUE)};  
            LinearGradient linear = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);  

            graphBars[i].setFill(linear);  
            
        }
    }
    
}
