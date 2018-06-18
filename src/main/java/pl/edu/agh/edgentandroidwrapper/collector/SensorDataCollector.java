package pl.edu.agh.edgentandroidwrapper.collector;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import lombok.Builder;
import org.apache.edgent.android.hardware.runtime.SensorSourceSetup;
import pl.edu.agh.edgentandroidwrapper.Topology.SimpleTopology;
import pl.edu.agh.edgentandroidwrapper.consumer.SensorDataConsumer;
import pl.edu.agh.edgentandroidwrapper.samplingrate.SamplingRate;

@Builder
public class SensorDataCollector {
    private int sensor;
    private SensorDataConsumer consumer;
    private SamplingRate samplingRate;
    private SimpleTopology simpleTopology;
    private Activity activity;

    public void startCollecting(SensorManager sensorManager) {
        Sensor sensorObject = sensorManager.getDefaultSensor(this.sensor);
        SensorSourceSetup source = new SensorSourceSetup(sensorManager, samplingRate.getValueInMicroseconds(), sensorObject);
        simpleTopology.submitTopology(activity, source, consumer);
    }

}
