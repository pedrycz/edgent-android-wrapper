package pl.edu.agh.edgentandroidwrapper.Topology;

import android.hardware.SensorEvent;
import lombok.Builder;
import lombok.Singular;
import org.apache.edgent.android.hardware.runtime.SensorSourceSetup;
import org.apache.edgent.function.Predicate;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.Topology;
import pl.edu.agh.edgentandroidwrapper.filter.Filter;

import java.util.ArrayList;
import java.util.List;

@Builder
public class FilteringTopology implements SimpleTopology<SensorEvent> {

    @Singular
    private List<Filter> predefinedFilters = new ArrayList<>();
    @Singular
    private List<Predicate<SensorEvent>> userFilters = new ArrayList<>();
    private String tag;
    private String name;

    public TStream<SensorEvent> getStream(SensorSourceSetup source, Topology topology) {
        TStream<SensorEvent> events = topology.events(source);

        for (Filter filter : predefinedFilters)
            events = events.filter(filter.getFilter());

        for (Predicate<SensorEvent> predicate : userFilters)
            events = events.filter(predicate);

        return events.tag(tag);
    }

    @Override
    public Topology getTopology() {
        return directProvider.newTopology(name);
    }
}
