package pl.edu.agh.edgentandroidwrapper.Topology;

import android.app.Activity;
import org.apache.edgent.android.hardware.runtime.SensorSourceSetup;
import org.apache.edgent.providers.direct.DirectProvider;
import org.apache.edgent.topology.Topology;
import pl.edu.agh.edgentandroidwrapper.consumer.SensorDataConsumer;

public abstract class SimpleTopology {
    protected String name;
    protected DirectProvider directProvider = new DirectProvider();

    Topology getTopology() {
        return directProvider.newTopology(name);
    }

    public abstract void submitTopology(Activity activity, SensorSourceSetup source, SensorDataConsumer consumer);

}
