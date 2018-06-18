package pl.edu.agh.edgentandroidwrapper.filter;

import android.hardware.SensorEvent;
import lombok.Builder;
import org.apache.edgent.function.Predicate;


@Builder
public class ValueInRangeFilter implements Filter {

    private Double rangeStart;
    private Double rangeEnd;

    @Override
    public Predicate<SensorEvent> getFilter() {
        return tuple -> tuple.values[0] >= rangeStart && tuple.values[0] <= rangeEnd;
    }
}
