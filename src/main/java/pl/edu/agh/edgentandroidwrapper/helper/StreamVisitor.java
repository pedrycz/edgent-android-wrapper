package pl.edu.agh.edgentandroidwrapper.helper;

import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.Topology;

public interface StreamVisitor<T> {
    void register(Topology topology, TStream<T> stream);
}
