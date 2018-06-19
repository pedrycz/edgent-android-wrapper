package pl.edu.agh.edgentandroidwrapper;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import pl.edu.agh.edgentandroidwrapper.Topology.FilteringTopology;
import pl.edu.agh.edgentandroidwrapper.Topology.LastKTuplesTopology;
import pl.edu.agh.edgentandroidwrapper.Topology.MappingTopology;
import pl.edu.agh.edgentandroidwrapper.collector.SensorDataCollector;
import pl.edu.agh.edgentandroidwrapper.helper.MqttVisitor;
import pl.edu.agh.edgentandroidwrapper.filter.ValueHigherThanFilter;
import pl.edu.agh.edgentandroidwrapper.filter.ValueInRangeFilter;
import pl.edu.agh.edgentandroidwrapper.filter.ValueLowerThanFilter;
import pl.edu.agh.edgentandroidwrapper.helper.StreamVisitor;
import pl.edu.agh.edgentandroidwrapper.samplingrate.SamplingRate;
import pl.edu.agh.edgentandroidwrapper.task.EdgentTask;

import static android.hardware.Sensor.TYPE_AMBIENT_TEMPERATURE;
import static android.hardware.Sensor.TYPE_PRESSURE;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Samples {
    //sample usage of simple filtering topology
    public static void runSample() {
        StreamVisitor<SensorEvent> visitor = MqttVisitor.<SensorEvent>builder()
                .queueUrl("tcp://iot.eclipse.org:1883")
                .clientId("52033_client")
                .topic("/52033_topic")
                .build();

        StreamVisitor<SensorEvent> secondVisitor = MqttVisitor.<SensorEvent>builder()
                .queueUrl("tcp://iot.eclipse.org:1883")
                .clientId("52034_client")
                .topic("/52034_topic")
                .build();

        StreamVisitor<SensorEvent> thirdVisitor = MqttVisitor.<SensorEvent>builder()
                .queueUrl("tcp://iot.eclipse.org:1883")
                .clientId("52035_client")
                .topic("/52035_topic")
                .build();

        EdgentTask task = EdgentTask.builder()
                .sensorManager(null)//TODO should be passed from android
                .sensorDataCollector(
                        SensorDataCollector.builder()
                                .sensor(TYPE_AMBIENT_TEMPERATURE)
                                .samplingRate(
                                        SamplingRate.builder()
                                                .timeUnit(SECONDS)
                                                .value(2)
                                                .build()
                                )
                                .simpleTopology(
                                        FilteringTopology.builder()
                                                .name("This is topology name")
                                                .tag("TEMPERATURE")
                                                .predefinedFilter(
                                                        ValueInRangeFilter.builder()
                                                                .rangeStart(60.0)
                                                                .rangeEnd(90.0)
                                                                .build()
                                                )
                                                .build()
                                )
                                .streamVisitor(visitor)
                                .build())
                .sensorDataCollector(
                        SensorDataCollector.builder()
                                .sensor(TYPE_PRESSURE)
                                .samplingRate(SamplingRate.builder()
                                        .timeUnit(SECONDS)
                                        .value(5)
                                        .build()
                                )
                                .simpleTopology(
                                        FilteringTopology.builder()
                                                .name("This is second topology name")
                                                .tag("PRESSURE")
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
                                .streamVisitor(secondVisitor)
                                .build()
                )
                .sensorDataCollector(
                        SensorDataCollector.builder()
                                .sensor(Sensor.TYPE_RELATIVE_HUMIDITY)
                                .samplingRate(SamplingRate.builder()
                                        .timeUnit(SECONDS)
                                        .value(10)
                                        .build()
                                )
                                .simpleTopology(LastKTuplesTopology.builder()
                                        .name("last-10-tuples")
                                        .numberOfElementsToStore(10)
                                        .tag("last-ten-tuples")
                                        .build()
                                )
                                .streamVisitor(thirdVisitor)
                                .build()
                )
                .sensorDataCollector(
                        SensorDataCollector.builder()
                                .sensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
                                .samplingRate(SamplingRate.builder()
                                        .timeUnit(SECONDS)
                                        .value(30)
                                        .build()
                                )
                                .simpleTopology(
                                        MappingTopology.builder()
                                                .name("Mapping topology")
                                                .tag("absolute-values")
                                                .mapper(tuple -> (double) Math.abs(tuple.values[0]))
                                                .build()
                                )
                                .build()
                )
                .build();

        task.start();
    }
}
