package pl.edu.agh.edgentandroidwrapper.consumer;

import lombok.Builder;
import org.apache.edgent.function.Consumer;

@Builder
public class MqttSensorDataConsumer implements SensorDataConsumer {
    private String queueUrl;
    private String queueTopic;

    @Override
    public Consumer getConsumer() {
        //initialization of queue should be performed here
        //consumer using created queue should be returned as a result
        return null;
    }
}
