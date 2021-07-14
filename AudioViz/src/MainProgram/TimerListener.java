package MainProgram;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.media.AudioSpectrumListener;

public class TimerListener implements AudioSpectrumListener {
    @Override
    public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {

    }
}
