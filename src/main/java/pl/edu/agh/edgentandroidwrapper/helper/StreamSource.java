package pl.edu.agh.edgentandroidwrapper.helper;

import android.hardware.SensorEvent;
import lombok.Builder;
import org.apache.edgent.function.Function;
import org.apache.edgent.topology.TStream;


public class StreamSource<T> {
    private TStream<T> stream;
    private Function<T, byte[]> serializer;
    private Function<T, String> topic;
    private Function<T, Integer> qos;
    private Function<T, Boolean> retain;

    public StreamSource(TStream<T> stream, Function<T, byte[]> serializer, Function<T, String> topic, Function<T, Integer> qos, Function<T, Boolean> retain) {
        this.stream = stream;
        this.serializer = serializer;
        this.topic = topic;
        this.qos = qos;
        this.retain = retain;
    }

    public StreamSource(TStream<T> stream, Function<T, byte[]> serializer, String topic, int qos){
        this(stream, serializer, constFunction(topic), constFunction(qos), constFunction(false));
    }

    public TStream<T> getStream() {
        return stream;
    }

    public Function<T, byte[]> getSerializer() {
        return serializer;
    }

    public Function<T, Integer> getQos() {
        return qos;
    }

    public Function<T, Boolean> getRetain() {
        return retain;
    }

    public Function<T, String> getTopic() {
        return topic;
    }

    public static <T, V> Function<T, V> constFunction(final V value) {
        return new Function<T, V>() {
            @Override
            public V apply(T sensorEvent) {
                return value;
            }
        };
    }
}
