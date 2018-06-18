package pl.edu.agh.edgentandroidwrapper.samplingrate;

import lombok.Builder;

import java.util.concurrent.TimeUnit;

@Builder
public class SamplingRate {
    private int value;
    private TimeUnit timeUnit;

    public int getValueInMicroseconds() {
        return Math.toIntExact(timeUnit.toMicros(value));
    }
}
