package pl.edu.agh.edgentandroidwrapper.collector;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import lombok.Builder;
import org.apache.edgent.android.hardware.runtime.SensorSourceSetup;
import org.apache.edgent.android.topology.ActivityStreams;
import org.apache.edgent.providers.direct.DirectProvider;
import pl.edu.agh.edgentandroidwrapper.Topology.SimpleTopology;
import pl.edu.agh.edgentandroidwrapper.consumer.SensorDataConsumer;
import pl.edu.agh.edgentandroidwrapper.helper.StreamVisitor;
import pl.edu.agh.edgentandroidwrapper.samplingrate.SamplingRate;

import java.util.Optional;

@Builder
public class SensorDataCollector {
    private int sensor;
    private SensorDataConsumer consumer;
    private SamplingRate samplingRate;
    private SimpleTopology<SensorEvent> simpleTopology;
    private StreamVisitor<SensorEvent> streamVisitor;
    private Activity activity;

    private DirectProvider directProvider = new DirectProvider();

    public void startCollecting(SensorManager sensorManager) {
        Sensor sensorObject = sensorManager.getDefaultSensor(this.sensor);
        SensorSourceSetup source = new SensorSourceSetup(sensorManager, samplingRate.getValueInMicroseconds(), sensorObject);

        simpleTopology.submitTopology(activity, source, consumer, Optional.ofNullable(streamVisitor));
    }

}
