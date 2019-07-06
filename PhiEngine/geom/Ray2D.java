package PhiEngine.geom;

import PhiEngine.geom.transform.Rotate;
import PhiEngine.geom.transform.Transform;

import java.io.Serializable;
import java.util.Iterator;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Represents a ray using a position vector and a direction vector
 * Created by Alexander on 12/02/17.
 */
public class Ray2D implements Serializable{
    public Vector2D pos() {
        return position;
    }

    public Vector2D dir() {
        return direction;
    }

    private final Vector2D position;
    private final Vector2D direction;

    public Ray2D(Vector2D position, Vector2D direction){

        this.position = position;
        this.direction = direction;
    }

    public Ray2D(double x1, double y1, double x2, double y2){
        this(Vector2D.valueOf(x1,y1),Vector2D.valueOf(x2-x1,y2-y1));
    }

    /**
     * Create
     * @param position
     * @param dx
     * @param dy
     */
    public Ray2D(Vector2D position, double dx, double dy){
        this(position, Vector2D.valueOf(dx,dy));
    }

    public Vector2D intersection(Ray2D other){
       return direction.scaled(cast(other)).add(position);
    }

    /**
     * Calculates the scaling of the direction vector at the point that it intersects with the other line.
     * @param other the line to check for an intersections.
     * @return {@link Double#NaN} if the rays lie on the same line, {@link Double#POSITIVE_INFINITY} if they are parralel<br> and real number otherwise
     */
    public double cast(Ray2D other){
        Vector2D otherNormal = new Rotate(-90).applyLocal(other.direction);

        return other.position.sub(position).dot(otherNormal) / direction.dot(otherNormal);
    }

    public static Vector2D intersectionScaled(Ray2D ra,Ray2D rb){
        //TODO improve effeciency
        double m = rb.cast(ra);
        double n = ra.cast(rb);
        return Vector2D.valueOf(m,n);
    }

    public double dx(){return dir().x();}
    public double dy(){return dir().y();}

    public static Ray2D forPoints(Vector2D a, Vector2D b){
        return new Ray2D(a,b.sub(a));
    }
    /*public static Ray2D forPoints(Vector2D a, Vector2D b, Vector2D...points){
        Supplier<Stream<Vector2D>> allPoints = ()->Stream.concat(Stream.of(a,b),Stream.of(points));
        double xm = allPoints.get().mapToDouble(Vector2D::x).average().getAsDouble();
        double ym = allPoints.get().mapToDouble(Vector2D::y).average().getAsDouble();
        Iterator<Vector2D> itx = allPoints.get().mapToDouble(v->v.x()-xm).iterator(),
                ity = allPoints.get().mapToDouble(v->v.x()-xm).iterator();

        for()
    }*/
    public Vector2D forT(double t){
        return position.add(direction,t);
    }

    public double closestT(Vector2D other){
        Ray2D normal = new Ray2D(other,-dy(),dx());
        return cast(normal);
    }

    @Override
    public String toString() {
        return String.format("%s + Tx%s",position,direction);
    }
}
