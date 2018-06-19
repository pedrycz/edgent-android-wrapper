# edgent-android-wrapper

Simple wrapper which allows writing topologies that makes collecting and processing of Android sensors data easier.
It is based on [Edgent library](https://github.com/apache/incubator-edgent), mostly on it's 
[bindings for Android](https://github.com/apache/incubator-edgent/tree/develop/platforms/android).

## Getting started
Basically, to benefit from the library, user has to perform three steps:
1. Obtain Android's `SensorManager` instance.
2. Create `EdgentTask` defining `SensorDataCollector` objects (at least one for each sensors to be processed).
3. Start `EdgentTask` using `start()` method.

###Sensor Manager
In order to be able to collect data from device sensor, `EdgentTask` should be configured with implementation
of `SensorManger` class which, in Android, could be obtained in the following way:

```java
SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
```

After that, it could be passed to `EdgentTask` like below:
```java
...
EdgentTask task = EdgentTask.builder()
        .sensorManager(sensorManager)
...
```

### Available topologies
Library provides three main, predefined topologies that could be used to process sensors data stream in most common
manner: 
* Filtering Topology
* Last K Tuples Topology
* Mapping Topology

#### Filtering Topology

`FilteringTopology` allows user to define conditions that has to be meet by all tuples 
in resulting stream.
Sample usage:
```java
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
                                        .predefinedFilter(
                                                ValueInRangeFilter.builder()
                                                        .rangeStart(60.0)
                                                        .rangeEnd(90.0)
                                                        .build()
                                        )
                                        .build()
                        )
                        .consumer(consumer)
                        .build()
        ).build();

task.start();
```

You can specify filters on your own -  or you can use one of the few predefined:
* `ValueLowerThanFilter`
* `ValueHigherThanFilter`
* `ValueInRangeFilter`
Predefined filters should be pass to `predefinedFilter()` or `predefinedFilters()` method (former for single filter, latter for 
collection of filters). In case of user's own filters, they should be passed to `userFilter()` and `userFilters()` methods.

#### Last K Tuples Topology

`LastKTuplesTopology` will hold only K last tuples.
Sample usage:
```java
EdgentTask task = EdgentTask.builder()
        .sensorManager(sensorManager)
        .sensorDataCollector(
                SensorDataCollector.builder()
                        .sensor(Sensor.TYPE_ACCELEROMETER)
                        .samplingRate(
                                SamplingRate.builder()
                                        .timeUnit(SECONDS)
                                        .value(1000)
                                        .build()
                        )
                        .activity(this)
                        .simpleTopology(
                                LastKTuplesTopology.builder()
                                        .name("sample")
                                        .tag("sample-hehe")
                                        .numberOfElementsToStore(10)
                                        .build()
                        )
                        .consumer(consumer)
                        .build())
        .build()
```

#### Mapping Topology

`Mapping topology` allows user to specify operation to be performed on tuples before being consumed by consumer.
Sample usage:
```java
EdgentTask task = EdgentTask.builder()
        .sensorManager(sensorManager)
        .sensorDataCollector(SensorDataCollector.builder()
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
```

### Specifying sampling rate
Library provides implementation of `SamplingRate` class that gives the user ability to specify sampling rate of 
configured sensor. It is only necessary to set time unit and value.
Sample usage could be found below:
```java
SamplingRate rate = SamplingRate.builder()
        .timeUnit(TimeUnit.SECONDS)
        .value(10)
        .build();
```
Such object could be then set in any of `SensorDataCollector` object using `samplingRate` property, for instance:
```java
...
SensorDataCollector.builder()
        .sensor(Sensor.TYPE_PRESSURE)
        .samplingRate(rate)
...
```

The library contains few predefined value of sampling rate, available in `PredefinedSamplingRates` class:
* ONE_SECOND
* TEN_SECONDS
* FIVE_MINUTES
* TEN_MINUTES
* ONE_HOUR

### Sensor Data Consumers
Library provides interface for consuming data collected by `SensorDataCollector` objects - `SensorDataConsumer`.
It only requires one method to be implemented - `getConsumer()` that will return function consuming sensor data.

Authors provide one implementation of the interface that benefit from MQTT protocol - it could be found in 
`MqttSensorDataConsumer` class.

To configure an instance of the class, one could use following code:
```java
SensorDataConsumer consumer = MqttSensorDataConsumer.builder()
        .queueUrl("sample-url-of-the-queue")
        .queueTopic("sample-topic")
        .build();
```
Where `sample-url-of-the-queue` and `sample-topic` should be replaced with real-world values of queue url and name 
of destination topic.
Such an object could be then passed to `SensorDataCollector` instance in the following way:
```java
...
SensorDataCollector.builder()
        .sensor(Sensor.TYPE_PRESSURE)
        .samplingRate(configuredRate)
        .topology(configuredTopology)
        .consumer(consumer)
...
```

## Authors

Authors of the project:
* [Wojciech Grabis](https://github.com/wgrabis)
* [Piotr Pedrycz](https://github.com/ppedrycz)
* [Piotr Wąchała](https://github.com/wachala)

