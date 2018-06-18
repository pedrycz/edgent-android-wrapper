package pl.edu.agh.edgentandroidwrapper.task;

import android.hardware.SensorManager;
import lombok.Builder;
import lombok.Singular;
import pl.edu.agh.edgentandroidwrapper.collector.SensorDataCollector;

import java.util.ArrayList;
import java.util.List;

@Builder
public class EdgentTask {

    @Singular
    private List<SensorDataCollector> sensorDataCollectors = new ArrayList<>();
    private SensorManager sensorManager;

    public void start() {
        sensorDataCollectors
                .forEach(collector -> collector.startCollecting(sensorManager));
    }

}
