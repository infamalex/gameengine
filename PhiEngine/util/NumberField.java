package PhiEngine.util;

import javafx.beans.Observable;
import javafx.beans.value.ObservableNumberValue;

/**
 * Created by Alex on 06/11/2017.
 */
public abstract class NumberField extends AbstractField<Number> implements ObservableNumberValue {
    @Override
    public int intValue() {
        return getValue().intValue();
    }

    @Override
    public long longValue() {
        return getValue().longValue();
    }

    @Override
    public float floatValue() {
        return getValue().floatValue();
    }

    @Override
    public double doubleValue() {
        return getValue().doubleValue();
    }

}
