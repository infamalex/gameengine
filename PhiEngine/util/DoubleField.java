package PhiEngine.util;

import javafx.beans.Observable;
import javafx.beans.value.ObservableDoubleValue;

/**
 * Created by Alex on 06/11/2017.
 */
public class DoubleField extends NumberField implements ObservableDoubleValue{

    private double value;

    public DoubleField(double value){

        this.value = value;
    }
    @Override
    public double get() {
        return value;
    }

    @Override
    public Number getValue() {
        return value;
    }
}
