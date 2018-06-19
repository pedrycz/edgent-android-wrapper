package pl.edu.agh.edgentandroidwrapper.helper;

import lombok.Builder;
import org.apache.edgent.connectors.mqtt.MqttStreams;
import org.apache.edgent.function.Function;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.Topology;

@Builder
public class MqttVisitor<T> implements StreamVisitor<T> {
    private String queueUrl;
    private String clientId;
    private String topic;
    private Function<T, byte[]> serializer;
    private int qos = 0;

    @Override
    public void register(Topology topology, TStream<T> stream) {
        MqttStreams sensorQueue = new MqttStreams(topology, queueUrl, clientId);
        sensorQueue.publish(stream, StreamSource.constFunction(topic), serializer, StreamSource.constFunction(qos), StreamSource.constFunction(false));
    }
}
