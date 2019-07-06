package PhiEngine.util;

import java.io.ObjectOutputStream;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * Created by Alex on 06/11/2017.
 */
public class Field<T> extends AbstractField<T> {
    private Object value;
    private final Function getter;
    private final BiConsumer setter;

    Field(Object value, Function<?,?extends T> getter, BiConsumer<Field,?> bc){
        this.value =  value;
        this.getter = getter;
        this.setter = bc;
    }

    Object getRawValue(){
        return value;
    }
    void setValue(Object value) {
        this.value = value;
    }

    public T get(){
        return (T)getter.apply(value);
    }
    public void set(T t){
        T old = get();
        setter.accept(this, t);
        invalidate(old,get());
    }

    @Override
    public T getValue() {
        return get();
    }
}
