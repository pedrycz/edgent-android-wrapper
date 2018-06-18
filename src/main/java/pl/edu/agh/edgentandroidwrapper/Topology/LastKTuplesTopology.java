package pl.edu.agh.edgentandroidwrapper.Topology;

import android.hardware.SensorEvent;
import lombok.Builder;
import org.apache.edgent.android.hardware.runtime.SensorSourceSetup;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.Topology;

import static org.apache.edgent.function.Functions.unpartitioned;

@Builder
public class LastKTuplesTopology implements SimpleTopology {

    int numberOfElementsToStore;
    private String tag;
    private String name;

    public TStream getStream(SensorSourceSetup source, Topology topology) {
        TStream<SensorEvent> events = topology.events(source);
        events.last(numberOfElementsToStore, unpartitioned());

        return events.tag(tag);
    }

    @Override
    public Topology getTopology() {
        return directProvider.newTopology(name);
    }

}
