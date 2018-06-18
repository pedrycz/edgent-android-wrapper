package pl.edu.agh.edgentandroidwrapper.filter;

import android.hardware.SensorEvent;
import org.apache.edgent.function.Predicate;

public interface Filter {

    Predicate<SensorEvent> getFilter();

}
