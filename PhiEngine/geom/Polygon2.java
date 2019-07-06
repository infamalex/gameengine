package PhiEngine.geom;

import PhiEngine.geom.transform.Rotate;
import PhiEngine.geom.transform.Transform;
import PhiEngine.util.Clamp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 10/12/2017.
 */

public class Polygon2 extends Shape implements Serializable{
    public Vector2D[] points;

    public static Polygon2 create(double x1, double y1, double x2, double y2, double x3, double y3, double...coords){
        if(coords.length%2 == 1)
            throw new IllegalArgumentException();
        List<Vector2D> points = new ArrayList<>(3+ coords.length/2);
        points.add(Vector2D.valueOf(x1,y1));
        points.add(Vector2D.valueOf(x2,y2));
        points.add(Vector2D.valueOf(x3,y3));

        for (int x = 0,y=1; x < coords.length; x+=2,y=x+1) {
            points.add(Vector2D.valueOf(coords[x],coords[y]));

        }
        return new Polygon2(points.stream().toArray(Vector2D[]::new));
    }
    private Polygon2(Vector2D[] points){

        this.points = points;
    }

    public Vector2D getPoint(int index){
        return points[index];
    }

    @Override
    public boolean contains(Vector2D v, Transform t) {
        return contains(v, new TransformedPolyIterator(t));
    }


    private boolean contains(Vector2D point,PolyIterator it){
        int intersects = 0;
        Ray2D tester = new Ray2D(point,1,0);
        Vector2D p = it.get();
        do {
            Vector2D v = Ray2D.intersectionScaled(new Ray2D(p, it.toNext()),tester);
            if(0 <= v.y && v.y <= 1 && v.x() >=0)
                intersects++;
            p =it.next();
        }
        while(!it.first());

        return intersects%2==1;
    }

    @Override
    public Vector2D closest(Vector2D v) {
        return closest(new PolyIterator(),v);
    }

    @Override
    public Vector2D closest(Transform t, Vector2D v) {//FIXME swap order
        return closest(new TransformedPolyIterator(t),v);
    }

    public ShapeIterator iterator(Transform t){
        return new TransformedPolyIterator(t);
    }


    private Vector2D closest(ShapeIterator it,Vector2D v) {
        Transform rotate = new Rotate(-90);
        double minDistanceSqu = Double.POSITIVE_INFINITY;
        Vector2D closest = null;

        //for( !it.last();p = it.next())
        Vector2D p = it.get();
        do {
            Vector2D dir = it.toNext();

             Ray2D normal = new Ray2D(v,rotate.applyLocal(dir));
            Ray2D side = new Ray2D(p,dir);

            Vector2D closestOnLine = Clamp.clamp(0,1,side,side.cast(normal));
            //Vector2D closestOnLine = Clamp.clamp(0,1,side,normal.pos().sub(side.pos()).dot(side.dir()));
            double distanceSqu = closestOnLine.sub(v).magSqu();

            if (distanceSqu < minDistanceSqu){
                minDistanceSqu = distanceSqu;
                closest = closestOnLine;
            }
            p = it.next();
        }
        while (!it.first());

        return closest;
    }

    class PolyIterator extends ShapeIterator{


        @Override
        public int size() {
            return points.length;
        }

        @Override
        protected Vector2D getPoint(int n) {
            return Polygon2.this.getPoint(n);
        }
    }

    class TransformedPolyIterator extends PolyIterator{
        private Transform transform;

        public TransformedPolyIterator(Transform transform){

            this.transform = transform;
        }

        @Override
        protected Vector2D getPoint(int n) {
            return transform.apply(super.getPoint(n));
        }
    }
}
