package PhiEngine.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.function.*;

/**
 * Created by Alex on 30/10/2017.
 */


public class Fields {
    public static <T> Field<T> field(T initial, UnaryOperator<? extends T> getter, UnaryOperator<? extends T> setter){
        return new Field<>(initial,getter, Field::setValue);
    }

    public static <T,C> Field<T> field(C object, Function<C,? extends T> getter, BiConsumer<C,? extends T> setter){
        return new Field<>(object,getter,(f,v)->((BiConsumer)setter).accept(f.getRawValue(),v));
    }

    public static <T> UnaryOperator<T> get(){
        return (UnaryOperator<T> & Serializable) t->t;
    }

    public static <T,C> BiConsumer<C,T> privateSet(){return (BiConsumer<C,T> & Serializable)Fields::privateSetter;}
    private static <T,C> void privateSetter(C c, T t){
        throw new UnsupportedOperationException("Set operation is not accessible");
    }


}
