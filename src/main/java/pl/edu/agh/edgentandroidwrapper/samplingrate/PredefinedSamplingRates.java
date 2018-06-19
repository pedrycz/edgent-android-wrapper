package pl.edu.agh.edgentandroidwrapper.samplingrate;

import static java.util.concurrent.TimeUnit.*;

public class PredefinedSamplingRates {
    public static final SamplingRate ONE_SECOND = SamplingRate.builder()
            .timeUnit(SECONDS)
            .value(1)
            .build();
    public static final SamplingRate TEN_SECONDS = SamplingRate.builder()
            .timeUnit(SECONDS)
            .value(10)
            .build();
    public static final SamplingRate FIVE_MINUTES = SamplingRate.builder()
            .timeUnit(MINUTES)
            .value(5)
            .build();
    public static final SamplingRate TEN_MINUTES = SamplingRate.builder()
            .timeUnit(MINUTES)
            .value(10)
            .build();
    public static final SamplingRate ONE_HOUR = SamplingRate.builder()
            .timeUnit(HOURS)
            .value(1)
            .build();
}
