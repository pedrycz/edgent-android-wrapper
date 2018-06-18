package pl.edu.agh.edgentandroidwrapper.Topology;

import android.app.Activity;
import org.apache.edgent.android.hardware.runtime.SensorSourceSetup;
import org.apache.edgent.android.topology.ActivityStreams;
import org.apache.edgent.providers.direct.DirectProvider;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.Topology;
import pl.edu.agh.edgentandroidwrapper.consumer.SensorDataConsumer;

public interface SimpleTopology {

    DirectProvider directProvider = new DirectProvider();

    Topology getTopology();

    TStream getStream(SensorSourceSetup source, Topology topology);

    default void submitTopology(Activity activity, SensorSourceSetup source, SensorDataConsumer consumer) {
        Topology topology = getTopology();
        TStream dataStream = getStream(source, topology);

        ActivityStreams.sinkOnUIThread(activity, dataStream, consumer.getConsumer());

        directProvider.submit(topology);
    }

}
