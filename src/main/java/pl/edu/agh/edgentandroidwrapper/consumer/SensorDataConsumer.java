package pl.edu.agh.edgentandroidwrapper.consumer;

import org.apache.edgent.function.Consumer;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.Topology;
import pl.edu.agh.edgentandroidwrapper.Topology.SimpleTopology;

public interface SensorDataConsumer {
    Consumer getConsumer();
}
