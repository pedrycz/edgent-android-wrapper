#edgent-android-wrapper

Simple wrapper that makes writing edgent topologies based on Android sensors easier.

##Getting started
In order to create very basic topology, that will listen for values of temperature sensor (in range [60.0 F, 90.0 F]) 
and pressure sensor (in range (1010.0 to 1020.0 hPa)), following code should be executed:

```java
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        SensorDataConsumer consumer = MqttSensorDataConsumer.builder()
                .queueUrl("sample-url-of-the-queue")
                .queueTopic("sample-topic")
                .build();

        SensorDataConsumer secondConsumer = MqttSensorDataConsumer.builder()
                .queueUrl("sample-url-of-the-queue")
                .queueTopic("sample-topic-for-second-consumer")
                .build();

        EdgentTask task = EdgentTask.builder()
                .sensorManager(sensorManager)
                .sensorDataCollector(
                        SensorDataCollector.builder()
                                .sensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
                                .samplingRate(
                                        SamplingRate.builder()
                                                .timeUnit(TimeUnit.MICROSECONDS)
                                                .value(1000000)
                                                .build()
                                )
                                .simpleTopology(
                                        FilteringTopology.builder()
                                                .filter(
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
                                .sensor(Sensor.TYPE_PRESSURE)
                                .samplingRate(SamplingRate.builder()
                                        .timeUnit(TimeUnit.MICROSECONDS)
                                        .value(1000000)
                                        .build()
                                )
                                .simpleTopology(
                                        FilteringTopology.builder()
                                                .filter(
                                                        ValueHigherThanFilter.builder()
                                                                .value(1010.0)
                                                                .build()
                                                )
                                                .filter(
                                                        ValueLowerThanFilter.builder()
                                                                .value(1020.0)
                                                                .build()
                                                )
                                                .build()
                                )
                                .consumer(secondConsumer)
                                .build()
                )
                .build();

        task.start();
``` 

##Authors

Authors of the project:
* [Wojciech Grabis](https://github.com/wgrabis)
* [Piotr Pedrycz](https://github.com/ppedrycz)
* [Piotr Wąchała](https://github.com/wachala)

