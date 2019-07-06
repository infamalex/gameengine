package PhiEngine.geom;



/**
 * Created by Alexander on 10/02/17.
 */
public class GeoMath {
    public static boolean approxEquals(double a, double b){
        return Math.abs(a-b)< 1D/(1<<8);
    }
    public static double aproximate(double d){
        long rounded = Math.round(d);
        return approxEquals(d, rounded)? rounded:d;
    }

    public static double floorMod(double a, double b){
        return a - Math.floor(a/b) * b;
    }

    public static final Ray2D displacement(Ray2D motion, Vector2D acceleration, double dt){
        Vector2D p = motion.forT(dt).add(acceleration,dt*dt/2);
        Vector2D v = motion.dir().add(acceleration,dt);
        return new Ray2D(p,v);
    }

}
