package MainProgram.Scenes;

import MainProgram.SceneController;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import static java.lang.Integer.min;

public class Scene2Controller implements SceneController{
    private final String name = "Scene 2";
    
    private Integer numBands;
    private AnchorPane vizPane;
    
    private final Double bandHeightPercentage = 1.3;
    private final Double minWidth = 10.0;
    
    private Double width = 0.0;
    private Double height = 0.0;
    
    private Double bandWidth = 0.0;
    private Double bandHeight = 0.0;
    private Double halfBandHeight = 0.0;
    
    private final Double startHue = 260.0;
    
    private Rectangle[] rectangles;
    public Scene2Controller(){

    }

    @Override
    public void start(Integer numBands, AnchorPane vizPane) {
        end();
        
        this.numBands = numBands;
        this.vizPane = vizPane;
        
        height = vizPane.getHeight();
        width = vizPane.getWidth();
        System.out.print(height);
        bandWidth = width / numBands;
        bandHeight = height * bandHeightPercentage;
        halfBandHeight = bandHeight / 2;
        rectangles = new Rectangle[numBands];
        
        for (int i = 0; i < numBands; i++) {
            Rectangle rectangle = new Rectangle();
            rectangle.setX(bandWidth / 2 + bandWidth * i);
            rectangle.setY(height / 2);
            rectangle.setWidth(bandWidth);
            rectangle.setHeight(minWidth);
            rectangle.setFill(Color.hsb(startHue, 1.0, 1.0, 1.0));
            vizPane.getChildren().add(rectangle);
            rectangles[i] = rectangle;
        }
        
    }

    @Override
    public void end() {
        if (rectangles != null) {
            for (Rectangle rectangle : rectangles) {
                vizPane.getChildren().remove(rectangle);
            }
           rectangles = null;
       }
        
    }

    @Override
    public String getName() {

        return name;
    }

    @Override
    public void update(double timestamp, double duration, float[] magnitudes, float[] phases) {
        if (rectangles == null) {
            return;
        }
        
        Integer num = min(rectangles.length, magnitudes.length);
        
        for (int i = 0; i < num; i++) {
            System.out.println(magnitudes[i]);
            
            rectangles[i].setScaleY(2);
            rectangles[i].setHeight(((60.0 + magnitudes[i])/60.0) * halfBandHeight + minWidth);
            //rectangles[i].setHeight(rectangles[i].getY() + (height/2));
            rectangles[i].setFill(Color.hsb(startHue - (magnitudes[i] * -6.0), 1.0, 1.0, 1.0));
        }
        
    } 
}
