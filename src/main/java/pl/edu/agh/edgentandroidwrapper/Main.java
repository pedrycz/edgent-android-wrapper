package pl.edu.agh.edgentandroidwrapper;

import pl.edu.agh.edgentandroidwrapper.Topology.FilteringTopology;
import pl.edu.agh.edgentandroidwrapper.collector.SensorDataCollector;
import pl.edu.agh.edgentandroidwrapper.consumer.MqttSensorDataConsumer;
import pl.edu.agh.edgentandroidwrapper.consumer.SensorDataConsumer;
import pl.edu.agh.edgentandroidwrapper.filter.ValueHigherThanFilter;
import pl.edu.agh.edgentandroidwrapper.filter.ValueInRangeFilter;
import pl.edu.agh.edgentandroidwrapper.filter.ValueLowerThanFilter;
import pl.edu.agh.edgentandroidwrapper.samplingrate.SamplingRate;
import pl.edu.agh.edgentandroidwrapper.task.EdgentTask;

import static android.hardware.Sensor.TYPE_AMBIENT_TEMPERATURE;
import static android.hardware.Sensor.TYPE_PRESSURE;
import static java.util.concurrent.TimeUnit.MICROSECONDS;

public class Main {
    //sample usage of simple filtering topology
    public static void main(String[] args) {
        SensorDataConsumer consumer = MqttSensorDataConsumer.builder()
                .queueUrl("sample-url-of-the-queue")
                .queueTopic("sample-topic")
                .build();

        SensorDataConsumer secondConsumer = MqttSensorDataConsumer.builder()
                .queueUrl("sample-url-of-the-queue")
                .queueTopic("sample-topic-for-second-consumer")
                .build();

        EdgentTask task = EdgentTask.builder()
                .sensorManager(null)//TODO should be passed from android
                .sensorDataCollector(
                        SensorDataCollector.builder()
                                .sensor(TYPE_AMBIENT_TEMPERATURE)
                                .samplingRate(
                                        SamplingRate.builder()
                                                .timeUnit(MICROSECONDS)
                                                .value(1000000)
                                                .build()
                                )
                                .simpleTopology(
                                        FilteringTopology.builder()
                                                .name("This is topology name")
                                                .predefinedFilter(
                                                        ValueInRangeFilter.builder()
                                                                .rangeStart(60.0)
                                                                .rangeEnd(90.0)
                                                                .build()
                                                )
                                                .build()
                                )
                                .consumer(consumer)
                                .build())
                .sensorDataCollector(
                        SensorDataCollector.builder()
                                .sensor(TYPE_PRESSURE)
                                .samplingRate(SamplingRate.builder()
                                        .timeUnit(MICROSECONDS)
                                        .value(1000000)
                                        .build()
                                )
                                .simpleTopology(
                                        FilteringTopology.builder()
                                                .name("This is second topology name")
                                                .predefinedFilter(
                                                        ValueHigherThanFilter.builder()
                                                                .value(1010.0)
                                                                .build()
                                                )
                                                .predefinedFilter(
                                                        ValueLowerThanFilter.builder()
                                                                .value(1020.0)
                                                                .build()
                                                )
                                                .userFilter(tuple -> tuple.values[0] + tuple.values[1] == 23)
                                                .build()
                                )
                                .consumer(secondConsumer)
                                .build()
                )
                .build();

        task.start();
    }
}
