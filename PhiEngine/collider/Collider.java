package PhiEngine.collider;

import PhiEngine.comp.AbstractComponent;
import PhiEngine.comp.GameObject;
import PhiEngine.event.CollisionEvent;
import PhiEngine.geom.Vector2D;
import PhiEngine.geom.transform.Transform;

import java.awt.*;
import java.util.Optional;

/**
 * Created by Alex on 07/12/2017.
 */
public abstract class Collider extends AbstractComponent {
    public Collider(GameObject gameObject) {
        super(gameObject);
    }

    public abstract Optional<CollisionEvent> collide(Collider other);

    public Transform transform(){
        return getGameObject().transformField.get();
    }

    public abstract boolean contains(Vector2D v);
    public abstract void drawCollider(Graphics2D g);
}
