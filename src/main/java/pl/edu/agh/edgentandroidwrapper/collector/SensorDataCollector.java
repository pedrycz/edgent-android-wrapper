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

    public void startCollecting(SensorManager sensorManager) {
        simpleTopology.submitTopology(activity, sensorManager, this.sensor, consumer, Optional.ofNullable(streamVisitor));
    }

}
