package pl.edu.agh.edgentandroidwrapper.Topology;

import android.app.Activity;
import org.apache.edgent.android.hardware.runtime.SensorSourceSetup;
import org.apache.edgent.android.topology.ActivityStreams;
import org.apache.edgent.providers.direct.DirectProvider;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.Topology;
import pl.edu.agh.edgentandroidwrapper.consumer.SensorDataConsumer;
import pl.edu.agh.edgentandroidwrapper.helper.StreamVisitor;

import java.util.Optional;

public interface SimpleTopology<T> {

    DirectProvider directProvider = new DirectProvider();

    Topology getTopology();

    TStream<T> getStream(SensorSourceSetup source, Topology topology);

    default void submitTopology(Activity activity, SensorSourceSetup source, SensorDataConsumer consumer, Optional<StreamVisitor<T>> visitor) {
        Topology topology = getTopology();
        TStream<T> dataStream = getStream(source, topology);

        visitor.ifPresent(vis -> vis.register(topology, dataStream));

        ActivityStreams.sinkOnUIThread(activity, dataStream, consumer.getConsumer());

        directProvider.submit(topology);
    }

}
