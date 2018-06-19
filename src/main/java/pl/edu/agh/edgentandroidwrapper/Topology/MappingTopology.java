package pl.edu.agh.edgentandroidwrapper.Topology;

import android.hardware.SensorEvent;
import lombok.Builder;
import org.apache.edgent.android.hardware.runtime.SensorSourceSetup;
import org.apache.edgent.function.Function;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.Topology;

@Builder
public class MappingTopology implements SimpleTopology<SensorEvent> {

    private Function<SensorEvent, Double> mapper;
    private String tag;
    private String name;

    public TStream<SensorEvent> getStream(SensorSourceSetup source, Topology topology) {
        TStream<SensorEvent> events = topology.events(source);
        events.map(mapper);

        return events.tag(tag);
    }

    @Override
    public Topology getTopology() {
        return directProvider.newTopology(name);
    }

}
