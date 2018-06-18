package pl.edu.agh.edgentandroidwrapper.filter;

import android.hardware.SensorEvent;
import lombok.Builder;
import org.apache.edgent.function.Predicate;

@Builder
public class ValueLowerThanFilter implements Filter {
    private Double value;

    @Override
    public Predicate<SensorEvent> getFilter() {
        return tuple -> tuple.values[0] > value;
    }
}
