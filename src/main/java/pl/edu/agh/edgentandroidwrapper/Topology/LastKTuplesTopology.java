package pl.edu.agh.edgentandroidwrapper.Topology;

import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import lombok.Builder;
import org.apache.edgent.android.hardware.SensorStreams;
import org.apache.edgent.android.hardware.runtime.SensorSourceSetup;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.Topology;

import static org.apache.edgent.function.Functions.unpartitioned;

@Builder
public class LastKTuplesTopology implements SimpleTopology<SensorEvent> {

    int numberOfElementsToStore;
    private String tag;
    private String name;
    private Topology topology;

    public TStream<SensorEvent> getStream(SensorManager sensorManager, int sensor) {
        topology = directProvider.newTopology(name);
        TStream<SensorEvent> events = SensorStreams.sensors(topology, sensorManager, sensor);

        events.last(numberOfElementsToStore, unpartitioned());

        return events.tag(tag);
    }

    @Override
    public Topology getTopology() {
        return topology;
    }

}
