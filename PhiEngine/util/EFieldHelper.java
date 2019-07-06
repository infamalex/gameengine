package PhiEngine.util;

import PhiEngine.annotations.EField;
import PhiEngine.comp.GameObject;
import javafx.beans.Observable;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.logging.Logger;

/**
 * Created by Alex on 13/12/2017.
 */
public abstract class EFieldHelper {

    private static final Observable[] ARR = new Observable[0];
    private static final Logger LOGGER = Logger.getLogger(GameObject.class.getName());
    private static WeakHashMap<Object, Map<Observable, String[]>> map = new WeakHashMap<>();

    private static void add(Object o){
        if(map.containsKey(o))return;
        Map<Observable, String[]> fields = new HashMap<>();
        for(java.lang.reflect.Field f : o.getClass().getFields()){
            if(f.isAnnotationPresent(EField.class )){
                EField ann = f.getAnnotation(EField.class);
                try{
                    fields.put((Observable) f.get(o),ann.tags());
                } catch (IllegalAccessException  | ClassCastException e) {
                    LOGGER.warning(e.getMessage());
                }
            }
        }
        map.put(o,fields);
    }

    public static Observable[] getFields(Object o){
        add(o);
        return map.get(o).keySet().toArray(ARR);
    }

    private EFieldHelper(){}
}
