package PhiEngine.collider;

import PhiEngine.comp.GameObject;
import PhiEngine.event.CollisionEvent;
import PhiEngine.geom.Vector2D;

import java.awt.*;
import java.util.Optional;

/**
 * Created by Alex on 07/12/2017.
 */
public class CircleCollider extends Collider{

    private int radius;


    public CircleCollider(GameObject gameObject,int radius) {
        super(gameObject);

        this.radius = radius;
    }

    @Override
    public Optional<CollisionEvent> collide(Collider other) {
        if(other instanceof CircleCollider){
            CircleCollider otherCircle = (CircleCollider)other;
            Vector2D oPos= other.transform().getPosition();
            Vector2D toOther = oPos.sub(transform().getPosition());
            double distSqua = toOther.magSqu();
            double totalRad = radius + otherCircle.radius;

            if(totalRad*totalRad > distSqua){ //
                Vector2D closest = oPos.add(toOther.scaled(-otherCircle.radius/toOther.mag()));
                return Optional.of(new CollisionEvent(this.getGameObject(),other.getGameObject(),closest));
            }
        }
        else if(other instanceof PolygonCollider){
            return other.collide(this);
        }
        return Optional.empty();
    }

    @Override
    public boolean contains(Vector2D v) {
        return transform().getPosition().sub(v).magSqu() < radius*radius;
    }

    @Override
    public void drawCollider(Graphics2D g) {
        g.setColor(Color.RED);
        Vector2D pos = transform().getPosition();
        g.drawOval((int)(pos.x()-radius),(int)(pos.y()-radius), 2*radius, 2*radius);
    }

    public int getRadius() {
        return radius;
    }
}
