package PhiEngine.collider;

import PhiEngine.comp.GameObject;
import PhiEngine.event.CollisionEvent;
import PhiEngine.geom.*;
import PhiEngine.geom.transform.Rotate;
import PhiEngine.geom.transform.Transform;
import PhiEngine.geom.transform.Translate;
import PhiEngine.util.Clamp;

import java.awt.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Alex on 08/12/2017.
 */
public class PolygonCollider extends Collider {
    private final Polygon2 shape;

    public PolygonCollider(GameObject gameObject, Polygon2 shape) {
        super(gameObject);
        this.shape = shape;
    }

    @Override
    public Optional<CollisionEvent> collide(Collider other) {
        if(other instanceof CircleCollider){
            Vector2D otherPos = other.transform().getPosition();
            CircleCollider circleCollider = (CircleCollider)other;

            Vector2D closest = shape.closest(transform(),otherPos);
            double distanceSqu = closest.sub(otherPos).magSqu();
            if(distanceSqu<circleCollider.getRadius()*circleCollider.getRadius()){
                return Optional.of(new CollisionEvent(other.getGameObject(),this.getGameObject(),closest));

            }
            else return Optional.empty();

        }
        if(other instanceof FlexCollider)
            other.collide(this);
        return Optional.empty();
    }

    @Override
    public boolean contains(Vector2D v) {
        return shape.contains(v,transform());
    }


    @Override
    public void drawCollider(Graphics2D g) {
        g.setColor(Color.BLUE);
        ShapeIterator it = shape.iterator(transform());
        int[] xs = new int[it.size()+1], ys = new int[it.size()+1];

        int i = 1;
        xs[0] = (int)it.get().x();
        ys[0] = (int)it.get().y();
        do{
            Vector2D v = it.next();
            xs[i] = (int)v.x();
            ys[i] = (int)v.y();
            i++;
        }
        while (!it.first());
        Transform t = new Translate(5,5);
        Stream.of(shape.points).map(t).mapToDouble(Vector2D::x).mapToInt(n->(int)n).toArray();
        //int[] ys = Stream.of(shape.points).map(transform()).mapToDouble(Vector2D::y).mapToInt(n->(int)n).toArray();
        g.drawPolyline(xs,ys,it.size()+1);
    }
}
