package PhiEngine.event;

import PhiEngine.comp.GameObject;
import PhiEngine.geom.Vector2D;

import java.util.List;

/**
 * Created by Alex on 07/12/2017.
 */
public class CollisionEvent {
    public final GameObject o1, o2;
    public final List<Vector2D> contact;

    public CollisionEvent(GameObject o1, GameObject o2, Vector2D contact) {
        this(o1,o2,List.of(contact));
    }
    public CollisionEvent(GameObject o1, GameObject o2, List<Vector2D> contact) {
        this.o1 = o1;
        this.o2 = o2;
        this.contact = contact;
    }
}
