package PhiEngine.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * This class exists to help with the creation of collection of items such as listeners
 */
public class OnDemandHolder<T> {
    private List<T> items;

    private OnDemandHolder(T item){
        this.items = new ArrayList<>(List.of(item));
    }
    public static <T> OnDemandHolder<T> add(OnDemandHolder<T> holder, T t){
        if (holder == null)
            return new OnDemandHolder<>(t);
        else holder.items.add(t);
        return holder;
    }
    public static <T> boolean remove(OnDemandHolder<T> holder, T t){
        if (holder == null)
            return false;
        else return holder.items.remove(t);
    }
    public static <T> Stream<T> stream(OnDemandHolder<T> holder){
        if(holder == null) return Stream.empty();
        return holder.items.stream();
    }
}
