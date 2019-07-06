package PhiEngine.util;

import PhiEngine.geom.Ray2D;
import PhiEngine.geom.Vector2D;

/**
 * Created by Alex on 13/12/2017.
 */
public class Clamp {
    public static double clamp(double min, double max, double value){
        if(value < min)
            return min;
        if(value > max)
            return max;
        return value;
    }

    public static Vector2D clamp(double minT, double maxT, Ray2D line, double value){
        return line.forT(clamp(minT,maxT,value));
    }
}
