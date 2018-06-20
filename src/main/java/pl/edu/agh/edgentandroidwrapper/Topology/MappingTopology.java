package pl.edu.agh.edgentandroidwrapper.Topology;

import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import lombok.Builder;
import org.apache.edgent.android.hardware.SensorStreams;
import org.apache.edgent.android.hardware.runtime.SensorSourceSetup;
import org.apache.edgent.function.Function;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.Topology;

@Builder
public class MappingTopology implements SimpleTopology<SensorEvent> {

    private Function<SensorEvent, Double> mapper;
    private String tag;
    private String name;
    private Topology topology;

    public TStream<SensorEvent> getStream(SensorManager sensorManager, int sensor) {
        topology = directProvider.newTopology(name);
        TStream<SensorEvent> events = SensorStreams.sensors(topology, sensorManager, sensor);

        events.map(mapper);

        return events.tag(tag);
    }

    @Override
    public Topology getTopology() {
        return directProvider.newTopology(name);
    }

}
