package pl.edu.agh.edgentandroidwrapper.Topology;

import android.app.Activity;
import android.hardware.SensorEvent;
import lombok.Builder;
import lombok.Singular;
import org.apache.edgent.android.hardware.runtime.SensorSourceSetup;
import org.apache.edgent.android.topology.ActivityStreams;
import org.apache.edgent.function.Predicate;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.Topology;
import pl.edu.agh.edgentandroidwrapper.consumer.SensorDataConsumer;
import pl.edu.agh.edgentandroidwrapper.filter.Filter;

import java.util.ArrayList;
import java.util.List;

@Builder
public class FilteringTopology extends SimpleTopology {

    @Singular
    private List<Filter> predefinedFilters = new ArrayList<>();
    @Singular
    private List<Predicate<SensorEvent>> userFilters = new ArrayList<>();
    private String name;

    public TStream getStream(SensorSourceSetup source) {
        Topology topology = getTopology();
        TStream<SensorEvent> events = topology.events(source);

        for (Filter filter : predefinedFilters)
            events = events.filter(filter.getFilter());

        for (Predicate<SensorEvent> predicate : userFilters)
            events = events.filter(predicate);

        return events;
    }

    public void submitTopology(Activity activity, SensorSourceSetup source, SensorDataConsumer consumer) {
        Topology topology = getTopology();
        TStream dataStream = getStream(source);
        ActivityStreams.sinkOnUIThread(activity, dataStream, consumer.getConsumer());

        directProvider.submit(topology);
    }

}
